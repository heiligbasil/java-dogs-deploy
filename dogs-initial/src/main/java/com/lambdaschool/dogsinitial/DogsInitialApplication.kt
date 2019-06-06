package com.lambdaschool.dogsinitial

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.web.servlet.DispatcherServlet
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@EnableWebMvc
@SpringBootApplication
open class DogsInitialApplication
{
    companion object
    {
        val EXCHANGE_NAME = "LambdaServer"
        val QUEUE_NAME_LOW = "LowPriorityQueue"
        val QUEUE_NAME_HIGH = "HighPriorityQueue"
        val QUEUE_NAME_ERROR = "ErrorQueue"

        private lateinit var ourDogList: DogList

        @JvmStatic
        fun main(args: Array<String>)
        {
            ourDogList = DogList()
            val ctx: ApplicationContext = SpringApplication.run(DogsInitialApplication::class.java, *args)

            val dispatcherServlet: DispatcherServlet = ctx.getBean("dispatcherServlet") as DispatcherServlet
            dispatcherServlet.setThrowExceptionIfNoHandlerFound(true)
        }

        fun getOurDogList(): DogList = ourDogList
    }

    @Bean
    open fun appExchange(): TopicExchange
    {
        return TopicExchange(EXCHANGE_NAME)
    }

    @Bean
    open fun appQueueHigh(): Queue
    {
        return Queue(QUEUE_NAME_HIGH)
    }

    @Bean
    open fun declareBindingHigh(): Binding
    {
        return BindingBuilder.bind(appQueueHigh()).to(appExchange()).with(QUEUE_NAME_HIGH)
    }

    @Bean
    open fun appQueueLow(): Queue
    {
        return Queue(QUEUE_NAME_LOW)
    }

    @Bean
    open fun declareBindingLow(): Binding
    {
        return BindingBuilder.bind(appQueueLow()).to(appExchange()).with(QUEUE_NAME_LOW)
    }

    @Bean
    open fun appQueueError(): Queue
    {
        return Queue(QUEUE_NAME_ERROR)
    }

    @Bean
    open fun declareBindingError(): Binding
    {
        return BindingBuilder.bind(appQueueError()).to(appExchange()).with(QUEUE_NAME_ERROR)
    }


    @Bean
    open fun producerJackson2MessageConverter(): Jackson2JsonMessageConverter
    {
        return Jackson2JsonMessageConverter()
    }

}