package com.example.danque.designs.proxy;

/**
 * 代理类
 * subject
 */
class ProxySubject implements Specialty {

    private RealSubject realSubject = new RealSubject();

    public void display() {
        preRequest();
        realSubject.display();
        postRequest();
    }
    public void preRequest() {
        System.out.println("韶关代理婺源特产开始。");
    }
    public void postRequest() {
        System.out.println("韶关代理婺源特产结束。");
    }
}
