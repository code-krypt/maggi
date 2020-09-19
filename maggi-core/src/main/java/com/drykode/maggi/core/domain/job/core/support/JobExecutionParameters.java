package com.drykode.maggi.core.domain.job.core.support;

import com.drykode.maggi.core.domain.enums.AccessPermission;
import com.drykode.maggi.core.domain.enums.MachineProfile;
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
public class JobExecutionParameters implements Serializable {

  private static final long serialVersionUID = -9217908123185562285L;

  private Integer quorumSize;

  private Map<AccessPermission, Boolean> accessPermissions;

  private MachineProfile machineProfile;
}
