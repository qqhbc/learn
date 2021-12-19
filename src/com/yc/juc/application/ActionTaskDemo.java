package com.yc.juc.application;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ActionTaskDemo {
    private final ExecutorService actionExecutor = Executors.newCachedThreadPool();
    private final JButton startButton = new JButton();
    private final JButton cancelButton = new JButton();

    public void startAction() {
        startButton.addActionListener((event) -> {
            class CancelListener implements ActionListener {
                BackgroundTask<?> task;

                @Override
                public void actionPerformed(ActionEvent e) {
                    while (Objects.nonNull(task)) {
                        task.cancel(true);
                    }
                }
            }

            final CancelListener cancelListener = new CancelListener();
            cancelListener.task = new BackgroundTask<String>() {
                @Override
                protected String computer() throws Exception {
                    while (someWork() && isCancelled()) {
                        doSomeWork();
                    }
                    cancel(true);
                    return null;
                }

                protected boolean someWork() {
                    return true;
                }

                protected void doSomeWork() {
                }

                @Override
                protected void onComputer(String value, Throwable throwable, boolean isCancel) {
                    cancelButton.removeActionListener(cancelListener);
                }
            };
            cancelButton.addActionListener(cancelListener);
            actionExecutor.execute(cancelListener.task);
        });
    }

}
