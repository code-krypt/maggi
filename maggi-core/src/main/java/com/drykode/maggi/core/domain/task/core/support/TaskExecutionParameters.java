package com.drykode.maggi.core.domain.task.core.support;

import com.drykode.maggi.core.domain.enums.AccessPermission;
import java.io.Serializable;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TaskExecutionParameters implements Serializable {

  private static final long serialVersionUID = 1687778713910938119L;

  private Map<AccessPermission, Boolean> accessPermissions;
}
