package com.wangjie.imageloadersample;

import android.graphics.Point;
import android.os.Bundle;
import android.widget.ListView;
import com.wangjie.androidinject.annotation.annotations.base.AILayout;
import com.wangjie.androidinject.annotation.annotations.base.AIView;
import com.wangjie.androidinject.annotation.annotations.dimens.AIScreenSize;
import com.wangjie.androidinject.annotation.present.AIActivity;

import java.util.ArrayList;
import java.util.List;

@AILayout(R.layout.main)
public class MainActivity extends AIActivity {
    @AIView(id = R.id.list)
    ListView lv;
    List<String> list = new ArrayList<String>();
    @AIScreenSize
    public Point screenSize;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        list.add("http://b.hiphotos.baidu.com/image/w%3D1366%3Bcrop%3D0%2C0%2C1366%2C768/sign=427fa35e014f78f0800b9ef04f073136/3ac79f3df8dcd1000333c52b708b4710b9122f08.jpg");
        list.add("http://h.hiphotos.baidu.com/image/w%3D1366%3Bcrop%3D0%2C0%2C1366%2C768/sign=1a83b02592ef76c6d0d2ff28ab20c699/023b5bb5c9ea15cec98d820db4003af33a87b257.jpg");
        list.add("http://h.hiphotos.baidu.com/image/w%3D1366%3Bcrop%3D0%2C0%2C1366%2C768/sign=472196182ff5e0feee188d026a560fcb/5243fbf2b2119313074066b167380cd791238db4.jpg");
        list.add("http://f.hiphotos.baidu.com/image/w%3D1366%3Bcrop%3D0%2C0%2C1366%2C768/sign=3e35a6e060d9f2d3201120ec9fdab170/5bafa40f4bfbfbedeea5af7e7af0f736afc31f36.jpg");
        list.add("http://h.hiphotos.baidu.com/image/w%3D1366%3Bcrop%3D0%2C0%2C1366%2C768/sign=57ea386e97cad1c8d0bbf82449085c6a/b8389b504fc2d562e291f8ace51190ef77c66cd1.jpg");
        list.add("http://c.hiphotos.baidu.com/image/w%3D2048/sign=538833da0afa513d51aa6bde095555fb/359b033b5bb5c9ead7c9a5e5d739b6003af3b3fa.jpg");
        list.add("http://c.hiphotos.baidu.com/image/w%3D2048/sign=41893e992cdda3cc0be4bf2035d13801/5d6034a85edf8db1c1acdb620b23dd54564e7431.jpg");
        list.add("http://b.hiphotos.baidu.com/image/w%3D2048/sign=670ef855f4246b607b0eb574dfc01a4c/96dda144ad3459826bb043940ef431adcbef84fa.jpg");
        list.add("http://c.hiphotos.baidu.com/image/w%3D1366%3Bcrop%3D0%2C0%2C1366%2C768/sign=db7ddae49e82d158bb825db2b63c22bb/30adcbef76094b36a6db47e7a1cc7cd98d109d6a.jpg");
        list.add("http://g.hiphotos.baidu.com/image/w%3D1366%3Bcrop%3D0%2C0%2C1366%2C768/sign=0d820d384936acaf59e092ff4aefb673/0df3d7ca7bcb0a463472c5386963f6246b60af5b.jpg");
        list.add("http://f.hiphotos.baidu.com/image/w%3D1366%3Bcrop%3D0%2C0%2C1366%2C768/sign=a72815dc2c738bd4c421b63297bdbcb5/b7fd5266d0160924d684c81ad60735fae6cd3409.jpg");
        list.add("http://f.hiphotos.baidu.com/image/w%3D1366%3Bcrop%3D0%2C0%2C1366%2C768/sign=346c1698cf11728b302d8821fecaf8ad/ae51f3deb48f8c5471825c2638292df5e0fe7f62.jpg");
        list.add("http://d.hiphotos.baidu.com/image/w%3D1366%3Bcrop%3D0%2C0%2C1366%2C768/sign=d126cc304890f60304b098440f248878/d53f8794a4c27d1e89bf7bc519d5ad6eddc43811.jpg");
        list.add("http://f.hiphotos.baidu.com/image/w%3D1366%3Bcrop%3D0%2C0%2C1366%2C768/sign=df50f433b1de9c82a665fd8c5ab7bb69/0df431adcbef7609063e89952cdda3cc7cd99e86.jpg");
        list.add("http://d.hiphotos.baidu.com/image/w%3D2048/sign=c432b92f271f95caa6f595b6fd2f7e3e/b7fd5266d0160924f8201e4ad60735fae6cd345d.jpg");
        list.add("http://b.hiphotos.baidu.com/image/w%3D2048/sign=8b2191774890f60304b09b470d2ab21b/10dfa9ec8a1363276373df75938fa0ec09fac7dd.jpg");
        list.add("http://f.hiphotos.baidu.com/image/w%3D2048/sign=70d28627f1deb48ffb69a6dec4273b29/960a304e251f95ca5ce08ef0cb177f3e6709525a.jpg");
        list.add("http://g.hiphotos.baidu.com/image/w%3D2048/sign=444e060589d4b31cf03c93bbb3ee267f/54fbb2fb43166d2241e908d2442309f79152d2dd.jpg");
        list.add("http://b.hiphotos.baidu.com/image/w%3D2048/sign=e0bdb7576c061d957d4630384fcc0bd1/0b46f21fbe096b63bf98903f0e338744eaf8acd8.jpg");
        list.add("http://h.hiphotos.baidu.com/image/w%3D2048/sign=25ff7ee4f0d3572c66e29bdcbe2b6227/8644ebf81a4c510ff6d7cb176259252dd42aa5af.jpg");
        list.add("http://e.hiphotos.baidu.com/image/w%3D2048/sign=dd63ba065b82b2b7a79f3ec40595cbef/b58f8c5494eef01f460e3ac7e2fe9925bc317d9d.jpg");
        list.add("http://e.hiphotos.baidu.com/image/w%3D2048/sign=14553e304e086e066aa8384b36307af4/7acb0a46f21fbe09c7af611569600c338744ad3a.jpg");
        list.add("http://a.hiphotos.baidu.com/image/w%3D2048/sign=ea5289a9eb50352ab1612208677bfaf2/2e2eb9389b504fc205c3361be7dde71190ef6d14.jpg");
        list.add("http://c.hiphotos.baidu.com/image/w%3D2048/sign=c137a568f01fbe091c5ec4145f580d33/64380cd7912397dd1bb8021a5b82b2b7d1a287ef.jpg");
        list.add("http://g.hiphotos.baidu.com/image/w%3D2048/sign=7ecf5f1da918972ba33a07cad2f57a89/b8014a90f603738d07ae6f2bb11bb051f919ecd7.jpg");
        list.add("http://c.hiphotos.baidu.com/image/w%3D2048/sign=64f997118594a4c20a23e02b3acc1ad5/aa64034f78f0f7361db23a1f0855b319ebc4135b.jpg");
        list.add("http://e.hiphotos.baidu.com/image/w%3D2048/sign=6c25aeba8718367aad8978dd1a4b8ad4/09fa513d269759eee1190358b3fb43166c22dfdc.jpg");
        list.add("http://g.hiphotos.baidu.com/image/w%3D2048/sign=67fce58ed2a20cf44690f9df42314a36/95eef01f3a292df5de80ce1cbe315c6034a87373.jpg");
        list.add("http://g.hiphotos.baidu.com/image/w%3D2048/sign=4d5324ca34d12f2ece05a9607bfad462/d009b3de9c82d158fa001cac820a19d8bc3e4228.jpg");
        list.add("http://a.hiphotos.baidu.com/image/w%3D2048/sign=c5b5c73a38f33a879e6d071af2641138/55e736d12f2eb9388bdc3ef8d7628535e5dd6f02.jpg");
        list.add("http://h.hiphotos.baidu.com/image/w%3D2048/sign=b87b591d40a7d933bfa8e3739973d013/8718367adab44aed99bbddedb11c8701a08bfbc6.jpg");
        list.add("http://b.hiphotos.baidu.com/image/w%3D2048/sign=9b2df23649fbfbeddc59317f4cc8f636/267f9e2f07082838deb2bf1dba99a9014c08f178.jpg");
        list.add("http://c.hiphotos.baidu.com/image/w%3D2048/sign=e049e7306963f6241c5d3e03b37ceaf8/902397dda144ad344bd4eb8ed2a20cf431ad854b.jpg");
        list.add("http://e.hiphotos.baidu.com/image/w%3D2048/sign=7c74511da918972ba33a07cad2f57a89/b8014a90f603738d0515612bb11bb051f819ec58.jpg");
        list.add("http://a.hiphotos.baidu.com/image/w%3D2048/sign=880cce1cbe315c6043956cefb989ca13/c83d70cf3bc79f3d902e8639b8a1cd11738b29ef.jpg");
        list.add("http://b.hiphotos.baidu.com/image/w%3D2048/sign=0e2ccab6a2ec08fa260014a76dd63c6d/2934349b033b5bb530c54ef734d3d539b600bcad.jpg");
        list.add("http://e.hiphotos.baidu.com/image/w%3D2048/sign=d593779634d3d539c13d08c30ebfe850/203fb80e7bec54e72aa84d76bb389b504fc26a15.jpg");
        list.add("http://d.hiphotos.baidu.com/image/w%3D2048/sign=7cc0d9f0db33c895a67e9f7be52b72f0/377adab44aed2e73f830284b8501a18b86d6faf8.jpg");
        list.add("http://b.hiphotos.baidu.com/image/w%3D2048/sign=6b7bb1fd552c11dfded1b823571f63d0/eaf81a4c510fd9f9de882377272dd42a2834a414.jpg");
        list.add("http://d.hiphotos.baidu.com/image/w%3D2048/sign=bf88e24e36a85edffa8cf9237d6c0823/3ac79f3df8dcd100b918733f708b4710b9122f23.jpg");


        lv.setAdapter(new MyAdapter(this, list));

    }


}
