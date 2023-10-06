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

clf = joblib.load("./Desktop/dianxin_model_SVC.m")
file1 = "./Desktop/背背佳数据/result.csv"
all_df1=pd.read_csv(file1)
dianxin_new = all_df1.sample(frac=1)
def show_accuracy_new(y_hat_new, y_test_new, param):
    pass
test_x1 = dianxin_new.iloc[:,:6]
test_y = clf.predict(test_x1)
print("新数据的结果=\n",test_y)
for i in range(len(test_y)):
    print(test_y[i])
a = max(test_y)
def show_accuracy_new(y_hat_new, y_test_new, param):
    pass
def data_change():
    for foldName, subfolders, filenames in os.walk(path):     #用os.walk方法取得path路径下的文件夹路径，子文件夹名，所有文件名
           for filename in filenames:                         #遍历列表下的所有文件名
                if filename== r'result.txt':                       #当文件名不为“001.txt”时
                    if filename.endswith('.txt'):                #当文件名以.txt后缀结尾时
                        new_name=filename.replace('.txt','.csv')               #为文件赋予新名字
                        shutil.copyfile(os.path.join(foldName,filename), os.path.join(foldName,new_name))    #复制并重命名文件
                        #print(filename,"copied as",new_name)           #输出提示
def data_deal():
    file1 = "./Desktop/背背佳数据/result.csv"
    all_df1=pd.read_csv(file1)
    dianxin_new = all_df1.sample(frac=1)
    test_x1 = dianxin_new.iloc[:,:8]
    test_y = clf.predict(test_x1)
    a = max(test_y)
    if a==0:
        data1="\r\n"
        a=str(a)+data1
        ser.write(a.encode(encoding="ascii"))
    else:
        #ser.write('1\r\n'.decode('gbk').encode('utf-8'))
        data1="\r\n"
        a=str(a)+data1
        ser.write(a.encode(encoding="ascii"))
    
def data_del():
    os.remove("./Desktop/背背佳数据/result.txt")
    os.remove("./Desktop/背背佳数据/result.csv")
while(1):
    data = ''
    for i in range(10):
        data = ser.readline()
        t = time.time()
        ct = time.ctime(t)
       # print(data)
        f = open('./Desktop/背背佳数据/result.txt', 'a')
        f.writelines(data.decode('utf-8'))
        f.close()
    data_change()
    data_deal()
    data_del()
    time.sleep(1)