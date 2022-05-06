package com.example.danque.designs.proxy;

import javax.swing.*;
import java.awt.*;

/**
 * 真实主题
 */
class RealSubject extends JFrame implements Specialty {
    private static final long serialVersionUID = 1L;
    public RealSubject() {
        super("韶关代理婺源特产测试");
        this.setLayout(new GridLayout(1, 1));
        JLabel l1 = new JLabel(new ImageIcon("C:/Users/lulu.wang.o/Desktop/宝宝封面照.jpg"));
        this.add(l1);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void display() {
        this.setVisible(true);
    }
}
