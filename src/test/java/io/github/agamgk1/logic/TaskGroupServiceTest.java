package io.github.agamgk1.logic;

import io.github.agamgk1.model.Task;
import io.github.agamgk1.model.TaskGroup;
import io.github.agamgk1.model.TaskGroupRepository;
import io.github.agamgk1.model.TaskRepository;
import io.github.agamgk1.model.projection.GroupReadModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskGroupServiceTest {


    @Test
    @DisplayName("should throw IllegalStateException when undone tasks exists")
    void toggleGroup_undone_tasks_exists_throws_IllegalStateException() {
        //given
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(true);
        //system under test
        var toTest = new TaskGroupService(null, mockTaskRepository);
        //when/then
        var exception = catchThrowable(() -> toTest.toggleGroup(1));
        assertThat(exception)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Group has undone tasks");
    }
    @Test
    @DisplayName("Should throw IllegalArgumentException when TaskGroup with given id not found")
    void toggleGroup_noTaskGroups_throws_IllegalArgumentException() {
        //given
        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(false);
        //and
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.empty());
        //system under test
        var toTest = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);
        //when + then
        var exception = catchThrowable(() -> toTest.toggleGroup(1));
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("given id not found");

    }
    @Test
    @DisplayName("should toggle TaskGroup with given id")
    void toggleGroup_with_existingTaskGroup() {
        //given
        var taskGroup = new TaskGroup();

        var mockTaskRepository = mock(TaskRepository.class);
        when(mockTaskRepository.existsByDoneIsFalseAndGroup_Id(anyInt())).thenReturn(false);
        //and
        var mockTaskGroupRepository = mock(TaskGroupRepository.class);
        when(mockTaskGroupRepository.findById(anyInt())).thenReturn(Optional.of(taskGroup));
        //system under test
        var toTest = new TaskGroupService(mockTaskGroupRepository, mockTaskRepository);
        //then
        boolean beforeToggle = taskGroup.isDone();
        toTest.toggleGroup(taskGroup.getId());
        boolean afterToggle = taskGroup.isDone();
        assertThat(beforeToggle).isNotEqualTo(afterToggle);
    }
}