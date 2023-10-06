import numpy as np
import matplotlib.pyplot as plt
from sklearn import svm,datasets
from pandas import DataFrame
import pandas as pd
from matplotlib import pyplot as plt
import matplotlib
from sklearn.preprocessing import StandardScaler
from sklearn import preprocessing
from sklearn import datasets
from sklearn.model_selection import train_test_split#分割训练集和测试集前自动打散数据
from sklearn import svm
from sklearn.preprocessing import Normalizer#归一化库函数
import numpy as np
from sklearn.svm import SVR #SVC解决多分类问题，SVR用于支持回归机做曲线拟合、函数回归 ，做预测，温度，天气，股票
import joblib
import matplotlib.pyplot as plt# 引入MATLAB相似绘图库，make_classification生成三元分类模型数据

file = "./Desktop/test.csv"
data=pd.read_csv(file)
data=data.values
x = data[:, :6]
y = data[:, 6]
def show_accuracy(y_hat, y_test, param):
    pass
x_train,x_test,y_train,y_test = train_test_split(x,y,random_state = 0,train_size = 0.3)

Normalizer().fit_transform(x_train)
Normalizer().fit_transform(x_test)
print("\n训练集精度：",linear_svc.score(x_train, y_train))   # 精度，socre函数评分
y_hat = linear_svc.predict(x_train)
show_accuracy(y_hat, y_train, '训练集')
print("\n测试集精度：",linear_svc.score(x_test, y_test))
y_hat = linear_svc.predict(x_test)
show_accuracy(y_hat, y_test, '测试集')
joblib.dump(linear_svc, "./Desktop/bbj_model_SVC.m")
