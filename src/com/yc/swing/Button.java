package com.yc.swing;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Button {
    public static void main(String[] args) {
        final Random random = new Random();
        final JButton button = new JButton();
        button.addActionListener(e -> button.setBackground(new Color(random.nextInt())));
    }
}
