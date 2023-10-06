#include "bsp_exti.h"

void EXTI_KEY_NVIC_Config(void)
{
	NVIC_InitTypeDef  NVIC_InitStruct;
	NVIC_PriorityGroupConfig(NVIC_PriorityGroup_2);
	NVIC_InitStruct.NVIC_IRQChannel = EXTI0_IRQn;
  NVIC_InitStruct.NVIC_IRQChannelPreemptionPriority = 1;
  NVIC_InitStruct.NVIC_IRQChannelSubPriority = 0;
  NVIC_InitStruct.NVIC_IRQChannelCmd = ENABLE;
  NVIC_Init(& NVIC_InitStruct);	
	
	
	NVIC_InitStruct.NVIC_IRQChannel = KEY2_INT_EXTI_IRQ;
  NVIC_InitStruct.NVIC_IRQChannelPreemptionPriority = 1;
  NVIC_InitStruct.NVIC_IRQChannelSubPriority = 1;
  NVIC_InitStruct.NVIC_IRQChannelCmd = ENABLE;
  NVIC_Init(& NVIC_InitStruct);	
}

void EXTI_Key_Config(void)
{
	GPIO_InitTypeDef  GPIO_InitStruct;
	EXTI_InitTypeDef  EXTI_InitStruct;
	//�����ж����ȼ� 
	EXTI_KEY_NVIC_Config();
	//��ʼ��gpio
	RCC_APB2PeriphClockCmd(KEY1_INT_GPIO_CLK, ENABLE);
	GPIO_InitStruct.GPIO_Pin   = KEY1_INT_GPIO_PIN;
	GPIO_InitStruct.GPIO_Mode  = GPIO_Mode_IN_FLOATING;
	GPIO_Init(KEY1_INT_GPIO_PORT, & GPIO_InitStruct);
	//��ʼ��exti
	RCC_APB2PeriphClockCmd(RCC_APB2Periph_AFIO, ENABLE);
	GPIO_EventOutputConfig(GPIO_PortSourceGPIOA , GPIO_PinSource0);
	
	EXTI_InitStruct.EXTI_Line = EXTI_Line0;
	EXTI_InitStruct.EXTI_Mode = EXTI_Mode_Interrupt;
	EXTI_InitStruct.EXTI_Trigger =EXTI_Trigger_Rising;
	EXTI_InitStruct.EXTI_LineCmd = ENABLE;
	EXTI_Init(&EXTI_InitStruct);
	/*--------------------------KEY2����-----------------------------*/
	/* ѡ�񰴼��õ���GPIO */	
  GPIO_InitStruct.GPIO_Pin = KEY2_INT_GPIO_PIN;
  /* ����Ϊ�������� */	
  GPIO_InitStruct.GPIO_Mode = GPIO_Mode_IN_FLOATING;
  GPIO_Init(KEY2_INT_GPIO_PORT,  &GPIO_InitStruct);

	/* ѡ��EXTI���ź�Դ */
  GPIO_EXTILineConfig(GPIO_PortSourceGPIOC, GPIO_PinSource13); 
  EXTI_InitStruct.EXTI_Line =EXTI_Line13;
	
	/* EXTIΪ�ж�ģʽ */
  EXTI_InitStruct.EXTI_Mode = EXTI_Mode_Interrupt;
	/* �½����ж� */
  EXTI_InitStruct.EXTI_Trigger = EXTI_Trigger_Rising;
  /* ʹ���ж� */	
  EXTI_InitStruct.EXTI_LineCmd = ENABLE;
  EXTI_Init(&EXTI_InitStruct);
}


