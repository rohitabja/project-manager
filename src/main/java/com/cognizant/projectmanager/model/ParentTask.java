package com.cognizant.projectmanager.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;

/**
 * Created by hp on 19-06-2019.
 */
@Builder
@Getter
@JsonPropertyOrder({"parentTaskId", "parentTaskName"})
public class ParentTask {

    private Integer parentTaskId;
    private String parentTaskName;

}
