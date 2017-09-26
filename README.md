# Maja [![](https://jitpack.io/v/billy96322/maja.svg)](https://jitpack.io/#billy96322/maja)

Protobuf, Flatbuffers都是非常优秀的序列化库，但是因为历史原因或是考虑到终端性能问题，许多项目任然使用私有的协议。当我们需要序列化和反序列化的时候，
经常需要写类似下面这样的代码。
如反序列化时：
```java
if (data.length >= 11) {
    Byte[] locationData = Arrays.copyOfRange(data, 0, 8);
    updateLocation(bikeStates, locationData);
    Byte[] heading = Arrays.copyOfRange(data, 8, 11);
    bikeStates.setGpsState(bitResolver(heading[0], 16));
}
if (data.length >= 14) {
    Byte[] signalData = Arrays.copyOfRange(data, 11, 14);
    updateSignal(bikeStates, signalData);
}
if (data.length >= 16) {
    Byte[] batteryData = Arrays.copyOfRange(data, 14, 16);
    updateVoltage(bikeStates, batteryData);
}
if (data.length >= 24) {
    Byte[] baseStationData = Arrays.copyOfRange(data, 16, 24);
    updateBaseStation(bikeStates, baseStationData);
}
if (data.length >= 44) {
    Byte[] controllerInfoData = Arrays.copyOfRange(data, 24, 44);
    resolveControllerState(bikeStates, controllerInfoData);
}
```
序列化时：
```java
RsaMsg rsaMsg = (RsaMsg) aBaseMsg;
byte[] result = new byte[132];
byte[] eBytes = ByteUtil.intToBytes(rsaMsg.e);
byte[] nBytes = rsaMsg.n;
System.arraycopy(eBytes, 0, result, 0, eBytes.length);
System.arraycopy(nBytes, 0 , result, eBytes.length, nBytes.length);
```
我们需要维护一个巨大的文件负责序列化和反序列化的工作，每次新增加字段都需要写重复的代码，如判断长度，拷贝数组等。
Maja是一个基于编译时注解的私有协议的序列化库，他帮助我们生成了繁琐重复的代码，是的序列化与反序列化变得简单清晰，一目了然。并且**未使用到反射**，
几乎不会带来任何性能的开销。

## 用法
因为Maja不使用反射，所以使用Maja可能会对你原有的实体类的写法造成破坏，它要求实体类所有使用注释的变量都为**public**修饰。
**@SerializedRule**注解中**start**参数传入需要(反)序列化该字段中对应的字符数组的其实位置，**length**为长度，而strategyIndex(可选)则是当(反)序列化有多种方法时作为
区分的标识符。
```java
public class State {
    @SerializedRule(start = 0, length = 4)
    public float battery;

    @SerializedRule(start = 4, length = 10, strategyIndex = LATLNG)
    public double[] location;

    @SerializedRule(start = 14, length = 1, strategyIndex = INT_ARRAY)
    public int[] signal;

    @SerializedRule(start = 15, length = 1)
    public int verifyFailedCode;
}
```

Maja的使用需要初始化，在初始化前先build一次项目，待**MajaSerializerDispatcher**文件生成后，初始化需要的文件才能被找到。
Maja本身并不会帮助做具体的解析，因此需要自行实现StrategyAdapter，做具体的从(反)序列化工作。
```java
public class APP extends Application {
    @Override
    public void onCreate() {
        ...
        Maja.init(new MyStrategyAdapter(), MajaSerializerDispatcher.instance());
    }
}
```
实现StrategyAdapter
```java
public class MyStrategyAdapter extends StrategyAdapter {
    ...
    @Override
    public int toInteger(byte[] data, int index) {
        return (data[0] & 0xff) << 24 | (data[1] & 0xff) << 16 | (data[2] & 0xff) << 8 | (data[3] & 0xff);;
    }
    
    @Override
    public byte[] from(int i, int lengthRequired, int index) {
        byte[] src = new byte[4];
        src[3] = (byte) ((i >> 24) & 0xFF);
        src[2] = (byte) ((i >> 16) & 0xFF);
        src[1] = (byte) ((i >> 8) & 0xFF);
        src[0] = (byte) (i & 0xFF);
        return src;
    }
    ...
}
```
(反)序列化
```java
State state = Maja.to(bytes, State.class);
byte[] serialized = Maja.from(state);
```

## 添加依赖到项目
#### gradle方式
在项目根目录的build.gradle添加
```gradle
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```

添加依赖(具体版本参照readme上的版本图标)
``` gradle
dependencies {

        compile 'com.github.billy96322.maja:core:x.y.z'
        
        annotationProcessor 'com.github.billy96322.maja:processor:x.y.z'
        // or apt 'com.github.billy96322.maja:processor:x.y.z'
}
