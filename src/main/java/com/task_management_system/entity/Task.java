package com.task_management_system.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Task")
@Table(name = "task")
@Getter
@Setter
public class Task extends AbstractEntity{

    @Column(name = "story")
    private String story;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false, foreignKey = @ForeignKey(name = "project_task_fk"))
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "user_task_fk"))
    private User user;
}
