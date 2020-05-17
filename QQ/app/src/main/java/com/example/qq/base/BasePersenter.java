package com.example.qq.base;

import com.example.qq.interfaces.IBaseView;
import com.example.qq.interfaces.IPresenter;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BasePersenter<V extends IBaseView> implements IPresenter<V> {

    protected V mView;

    //弱引用 view
    private WeakReference<V> weakReference;

    //rxjava2 数据加载的时候，界面回收一起的数据内存泄漏
    protected CompositeDisposable compositeDisposable;

    @Override
    public void attchView(V view) {
        weakReference = new WeakReference<>(view);
        this.mView = weakReference.get();
    }

    //解绑view的关联，同时解绑数据加载的关闭，避免出现内存泄漏
    @Override
    public void detachView() {
        this.mView = null;
        unSubscribe();
    }

    //解绑观察者和被观察者
    protected void unSubscribe(){
        if(compositeDisposable != null){
            compositeDisposable.clear();
        }
    }

    //添加观察者和被观察者的操作类
    //Disposable
    protected void addSubscribe(Disposable disposable){
        if(compositeDisposable == null) compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(disposable);
    }

    //添加带事件类型的观察者和被观察者
    /*protected <E> void addRxBusSubsribe(Class<E> eventType, Consumer<E> consumer){
        if(compositeDisposable == null) compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(RxBus.getDefault().toDefaultFlowable(eventType,consumer));
    }*/



}
