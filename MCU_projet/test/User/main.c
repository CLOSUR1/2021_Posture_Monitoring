#include "stm32f10x.h"
#include "bsp_usart.h"
#include "bsp_adc.h"
#include "bsp_key.h"
#include "bsp_exti.h"
#include "delay.h"
#include "sys.h"
#include "oled.h"
#include "bmp.h"
#define N 20

/* 注意事项-------------------------------------*/
/* 串口3与屏幕复用PB10，使用串口3时无法使用屏幕 */

uint16_t i;


//再次声明变量，用于保存转换计算后的电压值 	 
extern float ADC_ConvertedValue1[10];     
extern int aaa; 
// 软件延时
extern int bbb; 
extern int ccc; 
void  ADC_PRINT(void)//串口1发送函数
 {
	delay_ms(100);

	printf("[%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f] \r\n",
	ADC_ConvertedValue1[0],ADC_ConvertedValue1[1],ADC_ConvertedValue1[2],ADC_ConvertedValue1[3],ADC_ConvertedValue1[4],
	ADC_ConvertedValue1[5],ADC_ConvertedValue1[6],ADC_ConvertedValue1[7],ADC_ConvertedValue1[8],ADC_ConvertedValue1[9]);
	 
 }


int main(void)
{	
	
	int b2,b3,b4;
	USART_Config();// 配置串口
	USART3_Config();// 配置串口3
	Adc_Config();// ADC 初始化
  delay_init();//系统滴答定时器
	
//	OLED_Init();//oled屏幕初始化
//	OLED_ColorTurn(0);//0正常显示，1 反色显示
//  OLED_DisplayTurn(0);//0正常显示 1 屏幕翻转显示
//	OLED_Clear();//清屏

while(1)
	{	
//	OLED_Refresh();//屏幕刷新函数 必须有！！！
//	OLED_Show_ADC();//ADC值显示函数
		Adc_check();//传感器检测函数
	//	ADC_PRINT();//串口1发送函数
		switch(aaa){
		  case 1: 		//if(ccc==1){ USART_SendData(USART1,'z');
	delay_ms(100);
				if(b3<30){
		 printf("%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,1\n",
	ADC_ConvertedValue1[0],ADC_ConvertedValue1[1],ADC_ConvertedValue1[2],ADC_ConvertedValue1[3],ADC_ConvertedValue1[4],
	ADC_ConvertedValue1[5]);b3++;break;}
		else if(b3==30) 	{USART_SendData(USART3,'1');b3=0;break;}
		  case 2: 	
				delay_ms(100);
				if(b3<30){printf("%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,2\n",
	ADC_ConvertedValue1[0],ADC_ConvertedValue1[1],ADC_ConvertedValue1[2],ADC_ConvertedValue1[3],ADC_ConvertedValue1[4],
	ADC_ConvertedValue1[5]);b3++;break;}
				else if(b3==30){USART_SendData(USART3,'2');b3=0;break;}
		  case 3: delay_ms(100);
				if(b3<50){printf("%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,3\n",
	ADC_ConvertedValue1[0],ADC_ConvertedValue1[1],ADC_ConvertedValue1[2],ADC_ConvertedValue1[3],ADC_ConvertedValue1[4],
	ADC_ConvertedValue1[5]);b3++;break;}
				else if(b3==50) {USART_SendData(USART3,'3');b3=0;break;}
		  case 4:  delay_ms(100);
				if(b3<50){printf("%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,0\n",
	ADC_ConvertedValue1[0],ADC_ConvertedValue1[1],ADC_ConvertedValue1[2],ADC_ConvertedValue1[3],ADC_ConvertedValue1[4],
	ADC_ConvertedValue1[5]);b3++;break;}
				else if(b3==50) {USART_SendData(USART3,'4');b3=0;break;}
		  case 5:  delay_ms(100);
				if(b3<50){printf("%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,4\n",
	ADC_ConvertedValue1[0],ADC_ConvertedValue1[1],ADC_ConvertedValue1[2],ADC_ConvertedValue1[3],ADC_ConvertedValue1[4],
	ADC_ConvertedValue1[5]);b3++;break;}
					else if(b3==50){USART_SendData(USART3,'6');b3=0;aaa++;break;}
		  case 6:printf("stop"); aaa++;; break;
		}
		if(bbb==1){
		  delay_ms(100);
				if(b3<50){printf("%.3f,%.3f,%.3f,%.3f,%.3f,%.3f\n",
	ADC_ConvertedValue1[0],ADC_ConvertedValue1[1],ADC_ConvertedValue1[2],ADC_ConvertedValue1[3],ADC_ConvertedValue1[4],
	ADC_ConvertedValue1[5]);b3++;break;} 
				printf("o");
		}
	

}}
