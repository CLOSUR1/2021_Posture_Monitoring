#ifndef _BSP_EXTI_H_
#define _BSP_EXTI_H_

#include "stm32f10x.h"

#define KEY1_INT_GPIO_PIN         GPIO_Pin_0
#define KEY1_INT_GPIO_PORT        GPIOA
#define KEY1_INT_GPIO_CLK         RCC_APB2Periph_GPIOA
#define KEY2_INT_GPIO_PIN         GPIO_Pin_13
#define KEY2_INT_GPIO_PORT        GPIOC
#define KEY2_INT_GPIO_CLK         RCC_APB2Periph_GPIOC
#define KEY2_INT_EXTI_IRQ         EXTI15_10_IRQn
void EXTI_Key_Config(void);
void key1_led(void);
void EXTI_KEY2_NVIC_Config(void);
#endif
