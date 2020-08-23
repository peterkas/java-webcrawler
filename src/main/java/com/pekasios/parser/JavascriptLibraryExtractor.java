package com.pekasios.parser;

import com.pekasios.util.Connection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * Class to encapsulate Javascript Library Extractor logic
 *
 * @author Pedro Casis
 */
public class JavascriptLibraryExtractor {

    // Timeout in seconds for extractor tasks
    private static final int TASK_TIMEOUT = 10;

    // Executor Service
    private final ExecutorService executorService;

    public JavascriptLibraryExtractor() {
        this.executorService = Executors.newCachedThreadPool();;
    }

    public JavascriptLibraryExtractor(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public Map<String, Long> extractLibraries(List<String> resultLinks) {
        List<ExtractJavascriptLibrariesTask> tasks = resultLinks.stream()
                .map(link -> new ExtractJavascriptLibrariesTask(link))
                .collect(Collectors.toList());

        Map<String, Long> jsLibMap = new HashMap<>();
        try {
            List<Future<List<String>>> futureTasks = executorService.invokeAll(tasks, TASK_TIMEOUT, TimeUnit.SECONDS);
            while (!futureTasks.isEmpty()) {
                List<Future<List<String>>> finishedTasks = new ArrayList<>();
                for (Future<List<String>> task : futureTasks) {
                    if (task.isDone()) {
                        try {
                            mergeExtractedLibrariesIntoMap(task.get(), jsLibMap);
                        } catch (CancellationException ce) {
                            System.err.println("Task was cancelled due to a timeout");
                        } catch (InterruptedException|ExecutionException e) {
                            e.printStackTrace();
                        }
                        finishedTasks.add(task);
                    }
                }

                futureTasks.removeAll(finishedTasks);

                if (!futureTasks.isEmpty()) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
        return jsLibMap;
    }

    private void mergeExtractedLibrariesIntoMap(List<String> newJsLibs, Map<String, Long> mergedJsLibMap) {
        for (String jsLib : newJsLibs) {
            String jsLibName = jsLib.substring(jsLib.lastIndexOf("/") + 1).replaceAll("(\\.min)?\\.js", "");
            // TODO implement deduplication algorithms for the same Javascript libraries with different Names
            if (mergedJsLibMap.containsKey(jsLibName)) {
                mergedJsLibMap.put(jsLibName, mergedJsLibMap.get(jsLibName)+1);
            } else {
                mergedJsLibMap.put(jsLibName, 1L);
            }
        }
    }

    private static class ExtractJavascriptLibrariesTask implements Callable<List<String>> {
        private final String link;

        private ExtractJavascriptLibrariesTask(String link) {
            this.link = link;
        }

        @Override public List<String> call() {
            System.out.println("Extracting javascript libraries from [" + link + "]");
            List<String> list = WebParser.parseJavascriptLibraries(Connection.loadHTMLFromURL(link));
            System.out.println("Finished extracting from [" + link + "] found " + list.size() + " libraries");
            return list;
        }
    }
}
