#include "bsp_adc.h"
#include "delay.h"

float ADC_ConvertedValue1[10]={0};   //用于保存转换计算后的电压值


/*多通道的ADC采集*/
void  Adc_Config(void)
{ 	
    /*定义两个初始化要用的结构体，下面给每个结构体成员赋值*/
	ADC_InitTypeDef ADC_InitStructure; 
	GPIO_InitTypeDef GPIO_InitStructure;
	
	
		RCC_APB2PeriphClockCmd(RCC_APB2Periph_GPIOA |RCC_APB2Periph_GPIOB |RCC_APB2Periph_ADC1, ENABLE );	  
    RCC_ADCCLKConfig(RCC_PCLK2_Div6);  //72M/6=12, ADC的采样时钟最快14MHz  
      
    /*配置输入电压所用的PA0引脚*/  
		/*通道0*/
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_0;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPD; //GPIO_Mode_AIN：模拟输入（还有其他什么模式？请看下面的附录图1）
	GPIO_Init(GPIOA, &GPIO_InitStructure);	
		/*通道1*/
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_1;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPD; 
	GPIO_Init(GPIOA, &GPIO_InitStructure);	
			/*通道2*/
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_2;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPD; //GPIO_Mode_AIN：模拟输入（还有其他什么模式？请看下面的附录图1）
	GPIO_Init(GPIOA, &GPIO_InitStructure);	
			/*通道3*/
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_3;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPD; //GPIO_Mode_AIN：模拟输入（还有其他什么模式？请看下面的附录图1）
	GPIO_Init(GPIOA, &GPIO_InitStructure);	
			/*通道4*/
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_4;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPD; //GPIO_Mode_AIN：模拟输入（还有其他什么模式？请看下面的附录图1）
	GPIO_Init(GPIOA, &GPIO_InitStructure);	
				/*通道5*/
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_5;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPD; //GPIO_Mode_AIN：模拟输入（还有其他什么模式？请看下面的附录图1）
	GPIO_Init(GPIOA, &GPIO_InitStructure);	
				/*通道6*/
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_6;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPU; //GPIO_Mode_AIN：模拟输入（还有其他什么模式？请看下面的附录图1）
	GPIO_Init(GPIOA, &GPIO_InitStructure);	
				/*通道7*/
	GPIO_InitStructure.GPIO_Pin = GPIO_Pin_7;
	GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPU; //GPIO_Mode_AIN：模拟输入（还有其他什么模式？请看下面的附录图1）
	GPIO_Init(GPIOA, &GPIO_InitStructure);	
				/*通道8*/
		GPIO_InitStructure.GPIO_Pin = GPIO_Pin_0;
		GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPD; //GPIO_Mode_AIN：模拟输入（还有其他什么模式？请看下面的附录图1）
		GPIO_Init(GPIOB, &GPIO_InitStructure);	
		   /*通道9*/
		GPIO_InitStructure.GPIO_Pin = GPIO_Pin_1;
		GPIO_InitStructure.GPIO_Mode = GPIO_Mode_IPD; //GPIO_Mode_AIN：模拟输入（还有其他什么模式？请看下面的附录图1）
		GPIO_Init(GPIOB, &GPIO_InitStructure);	


	
	ADC_DeInit(ADC1); //复位，将ADC1相关的寄存器设为默认值
	ADC_InitStructure.ADC_Mode = ADC_Mode_Independent;	//工作模式：ADC1和ADC2独立工作模式  （还有其他什么模式？请看下面的附录图2）
	ADC_InitStructure.ADC_ScanConvMode = DISABLE;	//数模转换工作：扫描（多通道）模式=ENABLE、单次（单通道）模式=DISABLE
	ADC_InitStructure.ADC_ContinuousConvMode = DISABLE;//数模转换工作：连续=ENABLE、单次=DISABLE
	ADC_InitStructure.ADC_ExternalTrigConv = ADC_ExternalTrigConv_None;	//ADC转换由软件触发启动 （还有其他什么模式？请看下面的附录图3）
	ADC_InitStructure.ADC_DataAlign = ADC_DataAlign_Right;	//ADC数据右对齐   除了右就是左：ADC_DataAlign_Left
	ADC_InitStructure.ADC_NbrOfChannel = 1;	//顺序进行规则转换的ADC通道的数目   范围是1-16
	ADC_Init(ADC1, &ADC_InitStructure);	//根据ADC_InitStruct中指定的参数初始化外设ADC1的寄存器
 
    
	 ADC_RegularChannelConfig(ADC1, ADC_Channel_0, 1, ADC_SampleTime_239Cycles5 );	 //开启通道0
	 ADC_RegularChannelConfig(ADC1, ADC_Channel_1, 1, ADC_SampleTime_239Cycles5 );	 //开启通道1
	 ADC_RegularChannelConfig(ADC1, ADC_Channel_2, 1, ADC_SampleTime_239Cycles5 );	 //开启通道2
	 ADC_RegularChannelConfig(ADC1, ADC_Channel_3, 1, ADC_SampleTime_239Cycles5 );	 //开启通道3
	 ADC_RegularChannelConfig(ADC1, ADC_Channel_4, 1, ADC_SampleTime_239Cycles5 );	 //开启通道4
	 ADC_RegularChannelConfig(ADC1, ADC_Channel_5, 1, ADC_SampleTime_239Cycles5 );	 //开启通道5
	 ADC_RegularChannelConfig(ADC1, ADC_Channel_6, 1, ADC_SampleTime_239Cycles5 );	 //开启通道6
	 ADC_RegularChannelConfig(ADC1, ADC_Channel_7, 1, ADC_SampleTime_239Cycles5 );	 //开启通道7
	 ADC_RegularChannelConfig(ADC1, ADC_Channel_8, 1, ADC_SampleTime_239Cycles5 );	 //开启通道8
	 ADC_RegularChannelConfig(ADC1, ADC_Channel_9, 1, ADC_SampleTime_239Cycles5 );	 //开启通道9


	  		    
		ADC_Cmd(ADC1, ENABLE);	//使能指定的ADC  注意：函数ADC_Cmd只能在其他ADC设置函数之后被调用


	ADC_ResetCalibration(ADC1);	//重置指定的ADC的校准寄存器
	while(ADC_GetResetCalibrationStatus(ADC1)); //等待上一步操作完成
	ADC_StartCalibration(ADC1);	//开始指定ADC的校准状态	
	while(ADC_GetCalibrationStatus(ADC1));//等待上一步操作按成		
 }	

 
 void  Adc_check(void)
 {
	 
	  /*先采集通道0数据   PA0*/
	  ADC_RegularChannelConfig(ADC1, ADC_Channel_0, 1, ADC_SampleTime_239Cycles5 );
	  ADC_SoftwareStartConvCmd(ADC1, ENABLE);		
	  while(!ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC ));  
	  ADC_ConvertedValue1[0]=ADC_GetConversionValue(ADC1); 
		ADC_ConvertedValue1[0] =ADC_ConvertedValue1[0]/4096*3.3;
//		printf("\r\n 1号传感器电压 = %f \r\n",ADC_ConvertedValue1[0]);
	  delay_ms(10);

	  /*再采集通道1数据   PA1*/
	  ADC_RegularChannelConfig(ADC1, ADC_Channel_1, 1, ADC_SampleTime_239Cycles5 );
    ADC_SoftwareStartConvCmd(ADC1, ENABLE);		
	  while(!ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC ));  
	  ADC_ConvertedValue1[1]=ADC_GetConversionValue(ADC1); 
		ADC_ConvertedValue1[1] =ADC_ConvertedValue1[1]/4096*3.3;
//		printf("\r\n 2号传感器电压 = %f \r\n",ADC_ConvertedValue1[1]);
		delay_ms(10);
			  /*再采集通道2数据   PA2*/
	  ADC_RegularChannelConfig(ADC1, ADC_Channel_2, 1, ADC_SampleTime_239Cycles5 );
    ADC_SoftwareStartConvCmd(ADC1, ENABLE);		
	  while(!ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC ));  
	  ADC_ConvertedValue1[2]=ADC_GetConversionValue(ADC1); 
		ADC_ConvertedValue1[2] =ADC_ConvertedValue1[2]/4096*3.3;
//		printf("\r\n 3号传感器电压 = %f \r\n",ADC_ConvertedValue1[2]);
		delay_ms(10);
			  /*再采集通道3数据   PA3*/
	  ADC_RegularChannelConfig(ADC1, ADC_Channel_3, 1, ADC_SampleTime_239Cycles5 );
    ADC_SoftwareStartConvCmd(ADC1, ENABLE);		
	  while(!ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC ));  
	  ADC_ConvertedValue1[3]=ADC_GetConversionValue(ADC1); 
		ADC_ConvertedValue1[3] =ADC_ConvertedValue1[3]/4096*3.3;
//		printf("\r\n 4号传感器电压 = %f \r\n",ADC_ConvertedValue1[3]);
		delay_ms(10);
				  /*再采集通道4数据   PA4*/
	  ADC_RegularChannelConfig(ADC1, ADC_Channel_4, 1, ADC_SampleTime_239Cycles5 );
    ADC_SoftwareStartConvCmd(ADC1, ENABLE);		
	  while(!ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC ));  
	  ADC_ConvertedValue1[4]=ADC_GetConversionValue(ADC1); 
		ADC_ConvertedValue1[4] =ADC_ConvertedValue1[4]/4096*3.3;
//		printf("\r\n 5号传感器电压 = %f \r\n",ADC_ConvertedValue1[4]);
		delay_ms(10);
				  /*再采集通道5数据   PA5*/
	  ADC_RegularChannelConfig(ADC1, ADC_Channel_5, 1, ADC_SampleTime_239Cycles5 );
    ADC_SoftwareStartConvCmd(ADC1, ENABLE);		
	  while(!ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC ));  
	  ADC_ConvertedValue1[5]=ADC_GetConversionValue(ADC1); 
		ADC_ConvertedValue1[5] =ADC_ConvertedValue1[5]/4096*3.3;
//		printf("\r\n 6号传感器电压 = %f \r\n",ADC_ConvertedValue1[5]);
		delay_ms(10);
				  /*再采集通道6数据   PA6*/
	  ADC_RegularChannelConfig(ADC1, ADC_Channel_6, 1, ADC_SampleTime_239Cycles5 );
    ADC_SoftwareStartConvCmd(ADC1, ENABLE);		
	  while(!ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC ));  
	  ADC_ConvertedValue1[6]=ADC_GetConversionValue(ADC1); 
		ADC_ConvertedValue1[6] =ADC_ConvertedValue1[6]/4096*3.3;
//		printf("\r\n 7号传感器电压 = %f \r\n",ADC_ConvertedValue1[6]);
		delay_ms(10);
				  /*再采集通道7数据   PA7*/
	  ADC_RegularChannelConfig(ADC1, ADC_Channel_7, 1, ADC_SampleTime_239Cycles5 );
    ADC_SoftwareStartConvCmd(ADC1, ENABLE);		
	  while(!ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC ));  
	  ADC_ConvertedValue1[7]=ADC_GetConversionValue(ADC1); 
		ADC_ConvertedValue1[7] =ADC_ConvertedValue1[7]/4096*3.3;
//		printf("\r\n 8号传感器电压 = %f \r\n",ADC_ConvertedValue1[7]);
		delay_ms(10);
					  /*再采集通道8数据   PB0*/
	  ADC_RegularChannelConfig(ADC1, ADC_Channel_8, 1, ADC_SampleTime_239Cycles5 );
    ADC_SoftwareStartConvCmd(ADC1, ENABLE);		
	  while(!ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC ));  
	  ADC_ConvertedValue1[8]=ADC_GetConversionValue(ADC1); 
		ADC_ConvertedValue1[8] =ADC_ConvertedValue1[8]/4096*3.3;
//		printf("\r\n 9号传感器电压 = %f \r\n",ADC_ConvertedValue1[8]);
		delay_ms(10);
					  /*再采集通道9数据   PB1	*/
	  ADC_RegularChannelConfig(ADC1, ADC_Channel_9, 1, ADC_SampleTime_239Cycles5 );
    ADC_SoftwareStartConvCmd(ADC1, ENABLE);		
	  while(!ADC_GetFlagStatus(ADC1, ADC_FLAG_EOC ));  
	  ADC_ConvertedValue1[9]=ADC_GetConversionValue(ADC1); 
		ADC_ConvertedValue1[9] =ADC_ConvertedValue1[9]/4096*3.3;
//		printf("\r\n 10号传感器电压 = %f \r\n",ADC_ConvertedValue1[9]);
		delay_ms(10);	
}

