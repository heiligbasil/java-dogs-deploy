package com.lambdaschool.javadogsdeploy.controller

import com.lambdaschool.javadogsdeploy.CheckDog
import com.lambdaschool.javadogsdeploy.DogsInitialApplication
import com.lambdaschool.javadogsdeploy.exception.ResourceNotFoundException
import com.lambdaschool.javadogsdeploy.model.Dog
import com.lambdaschool.javadogsdeploy.model.MessageDetail
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/dogs")
class DogController
{
    companion object
    {
        private val logger = LoggerFactory.getLogger(DogController::class.java)
    }

    @Autowired
    internal var rabbitTemplate: RabbitTemplate? = null

    // localhost:2019/dogs/dogs
    @GetMapping(value = ["/dogs"], produces = ["application/json"])
    fun getAllDogs(request: HttpServletRequest): ResponseEntity<*>
    {
        val loggerDogs: Logger = LoggerFactory.getLogger(this.javaClass)

        val messageLog: String = "${request.requestURI} accessed on ${LocalDateTime.now()}"
        loggerDogs.info(messageLog)
        val message = MessageDetail(messageLog, 7, false)
        rabbitTemplate!!.convertAndSend(DogsInitialApplication.QUEUE_NAME_HIGH, message)


        val rtnDogs: MutableList<Dog> = DogsInitialApplication.getOurDogList().dogList

        if (rtnDogs.isEmpty())
        {
            throw ResourceNotFoundException("No results to display")
        }

        return ResponseEntity(rtnDogs, HttpStatus.OK)
    }

    // localhost:2019/dogs/{id}
    @GetMapping(value = ["/{id}"], produces = ["application/json"])
    fun getDogDetail(request: HttpServletRequest, @PathVariable id: Long): ResponseEntity<*>
    {
        val messageLog: String = "${request.requestURI} accessed with id $id on ${LocalDateTime.now()}"

        //logger.info(messageLog)
        val message = MessageDetail(messageLog, 1, true)
        rabbitTemplate!!.convertAndSend(DogsInitialApplication.QUEUE_NAME_LOW, message)


        val rtnDog: Dog? = DogsInitialApplication.getOurDogList().findDog(CheckDog { d -> d.id == id })

        if (rtnDog == null)
        {
            throw ResourceNotFoundException("No dog exists with id $id")
        }

        return ResponseEntity<Dog>(rtnDog, HttpStatus.OK)
    }

    // localhost:2019/dogs/breeds/{breed}
    @GetMapping(value = ["/breeds/{breed}"], produces = ["application/json"])
    fun getDogBreeds(request: HttpServletRequest, @PathVariable breed: String): ResponseEntity<*>
    {
        val messageLog: String = "${request.requestURI} accessed with breed $breed on ${LocalDateTime.now()}"
        //logger.info(messageLog)
        val message = MessageDetail(messageLog, 1, true)
        rabbitTemplate!!.convertAndSend(DogsInitialApplication.QUEUE_NAME_LOW, message)


        val rtnDogs: List<Dog> = DogsInitialApplication.getOurDogList().findDogs(CheckDog { d -> d.breed.toLowerCase().equals(breed.toLowerCase()) })

        if (rtnDogs.isEmpty())
        {
            throw ResourceNotFoundException("No dog breeds called $breed")
        }

        return ResponseEntity(rtnDogs, HttpStatus.OK)
    }

    //localhost:2019/dogs/dogtable
    @GetMapping(value = ["/dogtable"], produces = ["application/json"])
    fun displayDogTable(request: HttpServletRequest): ModelAndView
    {
        val messageLog: String = "${request.requestURI} accessed on ${LocalDateTime.now()}"
        logger.info(messageLog)
        val message = MessageDetail(messageLog, 1, true)
        rabbitTemplate!!.convertAndSend(DogsInitialApplication.QUEUE_NAME_LOW, message)


        val dogList: MutableList<Dog> = DogsInitialApplication.getOurDogList().dogList
        dogList.sortBy { it.breed }

        val mav = ModelAndView()
        mav.viewName = "dogs"
        mav.addObject("dogList", dogList)

        return mav
    }

    //localhost:2019/dogs/suitabledogtable
    @GetMapping(value = ["/suitabledogtable"], produces = ["application/json"])
    fun displaySuitableDogTable(request: HttpServletRequest): ModelAndView
    {
        val messageLog: String = "${request.requestURI} accessed on ${LocalDateTime.now()}"
        logger.info(messageLog)
        val message = MessageDetail(messageLog, 1, true)
        rabbitTemplate!!.convertAndSend(DogsInitialApplication.QUEUE_NAME_LOW, message)


        val dogList: List<Dog> = DogsInitialApplication.getOurDogList().dogList.filter { it.isApartmentSuitable }.sortedBy { a -> a.breed }

        val mav = ModelAndView()
        mav.viewName = "dogs"
        mav.addObject("dogList", dogList)

        return mav
    }
}