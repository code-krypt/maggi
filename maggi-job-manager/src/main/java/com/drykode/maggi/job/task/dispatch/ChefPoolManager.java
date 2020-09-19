package com.drykode.maggi.job.task.dispatch;

import com.drykode.maggi.core.domain.chefs.Chef;
import java.util.List;

public interface ChefPoolManager {

  Integer getChefCount();

  List<Chef> fetchChefs();
}
