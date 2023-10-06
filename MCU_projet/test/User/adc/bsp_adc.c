#include "bsp_adc.h"
#include "delay.h"

float ADC_ConvertedValue1[10]={0};   //���ڱ���ת�������ĵ�ѹֵ


/*��ͨ����ADC�ɼ�*/
void  Adc_Config(void)
{ 	
    /*����������ʼ��Ҫ�õĽṹ�壬�����ÿ���ṹ���Ա��ֵ*/
	ADC_InitTypeDef ADC_InitStructure; 
	GPIO_InitTypeDef GPIO_InitStructure;
	
	
		RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA |RCC_APB2Periph_GPIOB |RCC_APB2Periph_ADC1, ENABLE );	  
    RCC_ADCCLKConfig(RCC_PCLK2_Div6);  //72M/6=12, ADC�Ĳ���ʱ�����14MHz  
      
    /*���������ѹ���õ�PA0����*/  
		/*ͨ��0*/
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_0;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPD; //GPIO_Mode_AIN��ģ�����루��������ʲôģʽ���뿴����ĸ�¼ͼ1��
	GPIO_Init(GPIOA, &GPIO_InitStructure);	
		/*ͨ��1*/
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_1;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPD; 
	GPIO_Init(GPIOA, &GPIO_InitStructure);	
			/*ͨ��2*/
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_2;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPD; //GPIO_Mode_AIN��ģ�����루��������ʲôģʽ���뿴����ĸ�¼ͼ1��
	GPIO_Init(GPIOA, &GPIO_InitStructure);	
			/*ͨ��3*/
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_3;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPD; //GPIO_Mode_AIN��ģ�����루��������ʲôģʽ���뿴����ĸ�¼ͼ1��
	GPIO_Init(GPIOA, &GPIO_InitStructure);	
			/*ͨ��4*/
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_4;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPD; //GPIO_Mode_AIN��ģ�����루��������ʲôģʽ���뿴����ĸ�¼ͼ1��
	GPIO_Init(GPIOA, &GPIO_InitStructure);	
				/*ͨ��5*/
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_5;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPD; //GPIO_Mode_AIN��ģ�����루��������ʲôģʽ���뿴����ĸ�¼ͼ1��
	GPIO_Init(GPIOA, &GPIO_InitStructure);	
				/*ͨ��6*/
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_6;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPU; //GPIO_Mode_AIN��ģ�����루��������ʲôģʽ���뿴����ĸ�¼ͼ1��
	GPIO_Init(GPIOA, &GPIO_InitStructure);	
				/*ͨ��7*/
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_7;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPU; //GPIO_Mode_AIN��ģ�����루��������ʲôģʽ���뿴����ĸ�¼ͼ1��
	GPIO_Init(GPIOA, &GPIO_InitStructure);	
				/*ͨ��8*/
		GPIO_InitStructure.GPIO_Pin = GPIO_Pin_0;
		GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPD; //GPIO_Mode_AIN��ģ�����루��������ʲôģʽ���뿴����ĸ�¼ͼ1��
		GPIO_Init(GPIOB, &GPIO_InitStructure);	
		   /*ͨ��9*/
		GPIO_InitStructure.GPIO_Pin = GPIO_Pin_1;
		GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPD; //GPIO_Mode_AIN��ģ�����루��������ʲôģʽ���뿴����ĸ�¼ͼ1��
		GPIO_Init(GPIOB, &GPIO_InitStructure);	


	
	ADC_DeInit(ADC1); //��λ����ADC1��صļĴ�����ΪĬ��ֵ
	ADC_InitStructure.ADC_Mode = ADC_Mode_Independent;	//����ģʽ��ADC1��ADC2��������ģʽ  ����������ʲôģʽ���뿴����ĸ�¼ͼ2��
	ADC_InitStructure.ADC_ScanConvMode = DISABLE;	//��ģת��������ɨ�裨��ͨ����ģʽ=ENABLE�����Σ���ͨ����ģʽ=DISABLE
	ADC_InitStructure.ADC_ContinuousConvMode = DISABLE;//��ģת������������=ENABLE������=DISABLE
	ADC_InitStructure.ADC_ExternalTrigConv = ADC_ExternalTrigConv_None;	//ADCת��������������� ����������ʲôģʽ���뿴����ĸ�¼ͼ3��
	ADC_InitStructure.ADC_DataAlign = ADC_DataAlign_Right;	//ADC�����Ҷ���   �����Ҿ�����ADC_DataAlign_Left
	ADC_InitStructure.ADC_NbrOfChannel = 1;	//˳����й���ת����ADCͨ������Ŀ   ��Χ��1-16
	ADC_Init(ADC1, &ADC_InitStructure);	//����ADC_InitStruct��ָ���Ĳ�����ʼ������ADC1�ļĴ���
 
    
	 ADC_RegularChannelConfig(ADC1, ADC_Channel_0, 1, ADC_SampleTime_239Cycles5 );	 //����ͨ��0
	 ADC_RegularChannelConfig(ADC1, ADC_Channel_1, 1, ADC_SampleTime_239Cycles5 );	 //����ͨ��1
	 ADC_RegularChannelConfig(ADC1, ADC_Channel_2, 1, ADC_SampleTime_239Cycles5 );	 //����ͨ��2
	 ADC_RegularChannelConfig(ADC1, ADC_Channel_3, 1, ADC_SampleTime_239Cycles5 );	 //����ͨ��3
	 ADC_RegularChannelConfig(ADC1, ADC_Channel_4, 1, ADC_SampleTime_239Cycles5 );	 //����ͨ��4
	 ADC_RegularChannelConfig(ADC1, ADC_Channel_5, 1, ADC_SampleTime_239Cycles5 );	 //����ͨ��5
	 ADC_RegularChannelConfig(ADC1, ADC_Channel_6, 1, ADC_SampleTime_239Cycles5 );	 //����ͨ��6
	 ADC_RegularChannelConfig(ADC1, ADC_Channel_7, 1, ADC_SampleTime_239Cycles5 );	 //����ͨ��7
	 ADC_RegularChannelConfig(ADC1, ADC_Channel_8, 1, ADC_SampleTime_239Cycles5 );	 //����ͨ��8
	 ADC_RegularChannelConfig(ADC1, ADC_Channel_9, 1, ADC_SampleTime_239Cycles5 );	 //����ͨ��9


	  		    
		ADC_Cmd(ADC1, ENABLE);	//ʹ��ָ����ADC  ע�⣺����ADC_Cmdֻ��������ADC���ú���֮�󱻵���


	ADC_ResetCalibration(ADC1);	//����ָ����ADC��У׼�Ĵ���
	while(ADC_GetResetCalibrationStatus(ADC1)); //�ȴ���һ���������
	ADC_StartCalibration(ADC1);	//��ʼָ��ADC��У׼״̬	
	while(ADC_GetCalibrationStatus(ADC1));//�ȴ���һ����������		
 }	

 
 void  Adc_check(void)
 {
	 
	  /*�Ȳɼ�ͨ��0����   PA0*/
	  ADC_RegularChannelConfig(ADC1, ADC_Channel_0, 1, ADC_SampleTime_239Cycles5 );
	  ADC_SoftwareStartConvCmd(ADC1, ENABLE);		
	  while(!ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC ));  
	  ADC_ConvertedValue1[0]=ADC_GetConversionValue(ADC1); 
		ADC_ConvertedValue1[0] =ADC_ConvertedValue1[0]/4096*3.3;
//		printf("\r\n 1�Ŵ�������ѹ = %f \r\n",ADC_ConvertedValue1[0]);
	  delay_ms(10);

	  /*�ٲɼ�ͨ��1����   PA1*/
	  ADC_RegularChannelConfig(ADC1, ADC_Channel_1, 1, ADC_SampleTime_239Cycles5 );
    ADC_SoftwareStartConvCmd(ADC1, ENABLE);		
	  while(!ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC ));  
	  ADC_ConvertedValue1[1]=ADC_GetConversionValue(ADC1); 
		ADC_ConvertedValue1[1] =ADC_ConvertedValue1[1]/4096*3.3;
//		printf("\r\n 2�Ŵ�������ѹ = %f \r\n",ADC_ConvertedValue1[1]);
		delay_ms(10);
			  /*�ٲɼ�ͨ��2����   PA2*/
	  ADC_RegularChannelConfig(ADC1, ADC_Channel_2, 1, ADC_SampleTime_239Cycles5 );
    ADC_SoftwareStartConvCmd(ADC1, ENABLE);		
	  while(!ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC ));  
	  ADC_ConvertedValue1[2]=ADC_GetConversionValue(ADC1); 
		ADC_ConvertedValue1[2] =ADC_ConvertedValue1[2]/4096*3.3;
//		printf("\r\n 3�Ŵ�������ѹ = %f \r\n",ADC_ConvertedValue1[2]);
		delay_ms(10);
			  /*�ٲɼ�ͨ��3����   PA3*/
	  ADC_RegularChannelConfig(ADC1, ADC_Channel_3, 1, ADC_SampleTime_239Cycles5 );
    ADC_SoftwareStartConvCmd(ADC1, ENABLE);		
	  while(!ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC ));  
	  ADC_ConvertedValue1[3]=ADC_GetConversionValue(ADC1); 
		ADC_ConvertedValue1[3] =ADC_ConvertedValue1[3]/4096*3.3;
//		printf("\r\n 4�Ŵ�������ѹ = %f \r\n",ADC_ConvertedValue1[3]);
		delay_ms(10);
				  /*�ٲɼ�ͨ��4����   PA4*/
	  ADC_RegularChannelConfig(ADC1, ADC_Channel_4, 1, ADC_SampleTime_239Cycles5 );
    ADC_SoftwareStartConvCmd(ADC1, ENABLE);		
	  while(!ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC ));  
	  ADC_ConvertedValue1[4]=ADC_GetConversionValue(ADC1); 
		ADC_ConvertedValue1[4] =ADC_ConvertedValue1[4]/4096*3.3;
//		printf("\r\n 5�Ŵ�������ѹ = %f \r\n",ADC_ConvertedValue1[4]);
		delay_ms(10);
				  /*�ٲɼ�ͨ��5����   PA5*/
	  ADC_RegularChannelConfig(ADC1, ADC_Channel_5, 1, ADC_SampleTime_239Cycles5 );
    ADC_SoftwareStartConvCmd(ADC1, ENABLE);		
	  while(!ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC ));  
	  ADC_ConvertedValue1[5]=ADC_GetConversionValue(ADC1); 
		ADC_ConvertedValue1[5] =ADC_ConvertedValue1[5]/4096*3.3;
//		printf("\r\n 6�Ŵ�������ѹ = %f \r\n",ADC_ConvertedValue1[5]);
		delay_ms(10);
				  /*�ٲɼ�ͨ��6����   PA6*/
	  ADC_RegularChannelConfig(ADC1, ADC_Channel_6, 1, ADC_SampleTime_239Cycles5 );
    ADC_SoftwareStartConvCmd(ADC1, ENABLE);		
	  while(!ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC ));  
	  ADC_ConvertedValue1[6]=ADC_GetConversionValue(ADC1); 
		ADC_ConvertedValue1[6] =ADC_ConvertedValue1[6]/4096*3.3;
//		printf("\r\n 7�Ŵ�������ѹ = %f \r\n",ADC_ConvertedValue1[6]);
		delay_ms(10);
				  /*�ٲɼ�ͨ��7����   PA7*/
	  ADC_RegularChannelConfig(ADC1, ADC_Channel_7, 1, ADC_SampleTime_239Cycles5 );
    ADC_SoftwareStartConvCmd(ADC1, ENABLE);		
	  while(!ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC ));  
	  ADC_ConvertedValue1[7]=ADC_GetConversionValue(ADC1); 
		ADC_ConvertedValue1[7] =ADC_ConvertedValue1[7]/4096*3.3;
//		printf("\r\n 8�Ŵ�������ѹ = %f \r\n",ADC_ConvertedValue1[7]);
		delay_ms(10);
					  /*�ٲɼ�ͨ��8����   PB0*/
	  ADC_RegularChannelConfig(ADC1, ADC_Channel_8, 1, ADC_SampleTime_239Cycles5 );
    ADC_SoftwareStartConvCmd(ADC1, ENABLE);		
	  while(!ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC ));  
	  ADC_ConvertedValue1[8]=ADC_GetConversionValue(ADC1); 
		ADC_ConvertedValue1[8] =ADC_ConvertedValue1[8]/4096*3.3;
//		printf("\r\n 9�Ŵ�������ѹ = %f \r\n",ADC_ConvertedValue1[8]);
		delay_ms(10);
					  /*�ٲɼ�ͨ��9����   PB1	*/
	  ADC_RegularChannelConfig(ADC1, ADC_Channel_9, 1, ADC_SampleTime_239Cycles5 );
    ADC_SoftwareStartConvCmd(ADC1, ENABLE);		
	  while(!ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC ));  
	  ADC_ConvertedValue1[9]=ADC_GetConversionValue(ADC1); 
		ADC_ConvertedValue1[9] =ADC_ConvertedValue1[9]/4096*3.3;
//		printf("\r\n 10�Ŵ�������ѹ = %f \r\n",ADC_ConvertedValue1[9]);
		delay_ms(10);	
}

