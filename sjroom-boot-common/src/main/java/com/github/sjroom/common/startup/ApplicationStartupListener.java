package com.github.sjroom.common.startup;

import com.github.sjroom.common.response.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 容器端口启动完成后，执行该监听器
 *
 * @author huqichao
 * @date 2018-07-13 15:44
 */
@Slf4j
public class ApplicationStartupListener implements ApplicationListener<ApplicationReadyEvent> {


  private static List<StartupListener> list = new ArrayList<>();

  public static void addListener(StartupListener listener) {
    list.add(listener);
  }

  @Override
  public void onApplicationEvent(ApplicationReadyEvent event) {
    log.debug("启动监听器....");
    Collections.sort(list, new Comparator<StartupListener>() {
      @Override
      public int compare(StartupListener o1, StartupListener o2) {
        int order = o1.order() - o2.order();
        if (order == 0) {
          return o1.getClass().getName().compareTo(o2.getClass().getName());
        } else {
          return order;
        }
      }
    });

    final ApplicationContext context = event.getApplicationContext();

    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          for (StartupListener listener : list) {
            listener.start(context);
          }
        } catch (Exception e) {
          log.error("启动监听器异常", e);
          ((ConfigurableApplicationContext) context).close();
        }

        log.debug("启动监听器完成");
        log.debug("监听器启动是否成功...");
        for (StartupListener listener : list) {
          if (!listener.check(context)) {
            ((ConfigurableApplicationContext) context).close();
            R.throwBusinessException("启动异常:" + listener.getClass().getName());
          }
        }
        log.debug("监听器全部启动成功");
      }
    }).start();
  }
}
