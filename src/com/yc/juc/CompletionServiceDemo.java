package com.yc.juc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 通过CompletionService(Future + BlockingQueue)实现页面渲染(文本和图片)
 */
interface ImageInfo {
    ImageData downloadImageData();
}

interface ImageData {
}

public class CompletionServiceDemo {
    private final ExecutorService executorService;

    public CompletionServiceDemo(ExecutorService executorService) {
        this.executorService = executorService;
    }

    void renderPage(CharSequence source) {
        List<ImageInfo> list = scannerImage(source);
        CompletionService<ImageData> completionService = new ExecutorCompletionService<>(executorService);
        /* 一个一个图片下载解析 */
        for (ImageInfo info : list) {
            completionService.submit(info::downloadImageData);
        }
        renderText(source);
        for (int i = 0; i < list.size(); i++) {
            try {
                Future<ImageData> take = completionService.take();
                ImageData imageData = take.get();
                renderImage(imageData);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                throw ExceptionUtils.launderThrowable(e.getCause());
            }
        }
    }

    private void renderImage(ImageData imageData) {
    }

    private void renderText(CharSequence source) {
    }

    private List<ImageInfo> scannerImage(CharSequence source) {
        return new ArrayList<>();
    }
}
