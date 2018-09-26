package com.example.newtest.design;

import com.example.newtest.design.adapter.CN220V;
import com.example.newtest.design.adapter.CN220VImp;
import com.example.newtest.design.adapter.ElectricCooker;
import com.example.newtest.design.adapter.PowerAdapter;
import com.example.newtest.design.chain.PositionHandler;
import com.example.newtest.design.command.ChildEat;
import com.example.newtest.design.command.Command;
import com.example.newtest.design.command.Invoker;
import com.example.newtest.design.decorator.HatDecorator;
import com.example.newtest.design.decorator.PersonComponent;
import com.example.newtest.design.decorator.SweaterDecorator;
import com.example.newtest.design.facade.GameSdk;
import com.example.newtest.design.intermediary.Landlord;
import com.example.newtest.design.intermediary.Lianjia;
import com.example.newtest.design.intermediary.Purchaser;
import com.example.newtest.design.proxy.Domestic;
import com.example.newtest.design.proxy.DynamicProxy;
import com.example.newtest.design.proxy.IPeople;
import com.example.newtest.design.proxy.Oversea;
import com.example.newtest.design.state.ContextConcrete;
import com.example.newtest.design.strategy.ContextGuest;
import com.example.newtest.design.strategy.ContextGuestImprove;
import com.example.newtest.design.strategy.LiuJiRestaurant;
import com.example.newtest.design.strategy.SunJiRestaurant;
import com.example.newtest.design.strategy.WangJiRestaurant;
import com.example.newtest.net.HomePageObserver;
import com.example.newtest.net.NetWorkObservable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author Mark
 * @create 2018/9/25
 * @Describe
 */

public class Use {
    /**
     * 策略模式的应用
     */
    public void useStrategy(){
        LiuJiRestaurant liuJiRestaurant = new LiuJiRestaurant();
        WangJiRestaurant wangJiRestaurant = new WangJiRestaurant();
        SunJiRestaurant sunJiRestaurant = new SunJiRestaurant();
        ContextGuest contextGuest = new ContextGuest(liuJiRestaurant);
        contextGuest.haveBreakfast();
    }
    /**
     * 策略模式升级的应用
     */
    public void useStrategyImprove(){
        LiuJiRestaurant liuJiRestaurant = new LiuJiRestaurant();
        WangJiRestaurant wangJiRestaurant = new WangJiRestaurant();
        SunJiRestaurant sunJiRestaurant = new SunJiRestaurant();
        ContextGuestImprove contextGuestImprove = new ContextGuestImprove();
        contextGuestImprove.setStrategy(wangJiRestaurant);
        contextGuestImprove.haveBreakfast();
        contextGuestImprove.play();
    }
    /**
     * 装饰模式的应用
     */
    public void useDecorator(){
        PersonComponent personComponent =  new PersonComponent();
        SweaterDecorator sweaterDecorator = new SweaterDecorator(personComponent);
        HatDecorator hatDecorator = new HatDecorator(sweaterDecorator);
        hatDecorator.decorate();
    }

    /**
     * 观察者模式
     */
    public void useObserve(){
        NetWorkObservable.getInstance().register(new HomePageObserver());
    }

    public void useState(){
        ContextConcrete contextConcrete = new ContextConcrete();
        contextConcrete.fallInLove();
        contextConcrete.movies();
        contextConcrete.shopping();
        contextConcrete.disappointmentInLove();
        contextConcrete.movies();
        contextConcrete.shopping();
        contextConcrete.fallInLove();
    }

    public void useCommand(){
        Invoker invoker = new Invoker();
        ChildEat childEat = new ChildEat();
        Command command = new Command(childEat);
        invoker.setCommand(command);
        invoker.invoke();
    }

    public void useChain(){
        PositionHandler positionHandler1 =  new PositionHandler();
        PositionHandler positionHandler2 =  new PositionHandler();
        PositionHandler positionHandler3 =  new PositionHandler();
        PositionHandler positionHandler4 =  new PositionHandler();
        PositionHandler positionHandler5 =  new PositionHandler();
        positionHandler1.setPosition("科长");
        positionHandler2.setPosition("处长");
        positionHandler3.setPosition("局长");
        positionHandler4.setPosition("厅长");
        positionHandler5.setPosition("部长");

        positionHandler1.setHandler(positionHandler2);
        positionHandler2.setHandler(positionHandler3);
        positionHandler3.setHandler(positionHandler4);
        positionHandler4.setHandler(positionHandler5);
        positionHandler1.operate();
    }
    public void useStaticProxy(){
        Domestic domestic = new Domestic();
        Oversea oversea = new Oversea(domestic);
        oversea.buy();
    }
    public void useDynamicProxy(){
        IPeople domestic = new Domestic();

        DynamicProxy proxy = new DynamicProxy(domestic);

        ClassLoader classLoader = domestic.getClass().getClassLoader();

        IPeople overseaProxy = (IPeople) Proxy.newProxyInstance(classLoader,new Class[]{IPeople.class},proxy);
//        IPeople overseaProxy = (IPeople) Proxy.newProxyInstance(classLoader, new Class[]{IPeople.class}, new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                Object result = method.invoke(domestic, args);//调用被代理的对象的方法
//                return result;
//            }
//        });
        overseaProxy.buy();
    }

    public void useAdapter(){
        CN220V cn220 = new CN220VImp();// 220电压
        PowerAdapter powerAdapter = new PowerAdapter(cn220);
        ElectricCooker cooker = new ElectricCooker(powerAdapter);
        cooker.cook();
    }

    /**
     * 中介模式
     * 应用场景：
     * 在程序中，如果类的依赖关系过于复杂，呈现网状的结构，可以使用中介者模式对其进行解耦。
     * 优点：
     * 降低类的关系复杂度，将多对多转化成一对多，实现解耦。 符合迪米特原则，依赖的类最少。
     * 缺点：
     * 同事类越多，中介者的逻辑就越复杂，会变得越难维护。如果本来类的依赖关系不复杂，但是使用了中介者会使原来不复杂的逻辑变得复杂。因此需要根据实际情况去考虑，不要滥用中介者。
     */
    public void useIntermediary(){

        Lianjia lianjia = new Lianjia();
        Purchaser purchaser = new Purchaser(lianjia);
        Landlord landlord = new Landlord(lianjia);
        purchaser.send("我要买一套200的大房子");
        System.out.println("---------------------------");
        landlord.send("正好这有套200的房子很优惠");
    }

    /**
     * 外观模式
     * 使用场景：
     * 为一个复杂的子系统提供一个简单接口，对外隐藏子系统的具体实现、隔离变化。
     使用外观模式可以将一个子系统和使用它的客户端以及其它的子系统分离开来，这就提高了子系统的独立性和可移植性。
     在构建一个层次化结构的时候，可以使用外观模式定义每一个层次对外交互的接口。这样，层与层之间只需要通过外观进行通信，从而简化层与层之间的依赖关系。

     优点：
     降低了客户端与子系统类的耦合度，实现了子系统与客户之间的松耦合关系。
     外观类对子系统的接口封装，使得系统更易于使用。
     提高灵活性，不管子系统如何变化，只要不影响门面对象，就可以自由修改。
     缺点：
     增加新的子系统可能需要修改外观类的源代码，违背了“开闭原则”。
     所有子系统的功能都通过一个接口来提供，这个接口可能会变得很复杂。
     */
    public void useFacade(){
        GameSdk gameSdk = new GameSdk();
        gameSdk.login();
        gameSdk.pay(10);
    }
}
