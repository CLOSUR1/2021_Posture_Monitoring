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

/* ע������-------------------------------------*/
/* ����3����Ļ����PB10��ʹ�ô���3ʱ�޷�ʹ����Ļ */

uint16_t i;


//�ٴ��������������ڱ���ת�������ĵ�ѹֵ 	 
extern float ADC_ConvertedValue1[10];     
extern int aaa; 
// �����ʱ
extern int bbb; 
extern int ccc; 
void  ADC_PRINT(void)//����1���ͺ���
 {
	delay_ms(100);

	printf("[%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f,%.3f] \r\n",
	ADC_ConvertedValue1[0],ADC_ConvertedValue1[1],ADC_ConvertedValue1[2],ADC_ConvertedValue1[3],ADC_ConvertedValue1[4],
	ADC_ConvertedValue1[5],ADC_ConvertedValue1[6],ADC_ConvertedValue1[7],ADC_ConvertedValue1[8],ADC_ConvertedValue1[9]);
	 
 }


int main(void)
{	
	
	int b2,b3,b4;
	USART_Config();// ���ô���
	USART3_Config();// ���ô���3
	Adc_Config();// ADC ��ʼ��
  delay_init();//ϵͳ�δ�ʱ��
	
//	OLED_Init();//oled��Ļ��ʼ��
//	OLED_ColorTurn(0);//0������ʾ��1 ��ɫ��ʾ
//  OLED_DisplayTurn(0);//0������ʾ 1 ��Ļ��ת��ʾ
//	OLED_Clear();//����

while(1)
	{	
//	OLED_Refresh();//��Ļˢ�º��� �����У�����
//	OLED_Show_ADC();//ADCֵ��ʾ����
		Adc_check();//��������⺯��
	//	ADC_PRINT();//����1���ͺ���
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
