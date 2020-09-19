package com.drykode.maggi.job.task.dispatch.sse;

import com.drykode.maggi.core.domain.chefs.Chef;
import com.drykode.maggi.job.task.dispatch.ChefPoolManager;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class SseChefPoolManager implements ChefPoolManager {

  @Override
  public Integer getChefCount() {
    return 3;
  }

  @Override
  public List<Chef> fetchChefs() {
    return Collections.emptyList();
  }
}
