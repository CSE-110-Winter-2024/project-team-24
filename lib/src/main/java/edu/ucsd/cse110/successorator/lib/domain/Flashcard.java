package edu.ucsd.cse110.successorator.lib.domain;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Objects;

public class Flashcard implements Serializable {
    private final @Nullable Integer id;
    private final @NonNull String task;
    private final @NonNull Integer sortOrder;
    private final @NonNull boolean checkedOff;

    public Flashcard(@Nullable Integer id, @NonNull String task, int sortOrder, boolean checkedOff) {
        this.id = id;
        this.task = task;
        this.sortOrder = sortOrder;
        this.checkedOff = false;
    }

    public @Nullable Integer id() { return id; }
    public int sortOrder() { return sortOrder; }
    public Flashcard withSortOrder(int sortOrder) { return new Flashcard(this.id, this.task, sortOrder, checkedOff);  }
    public Flashcard withId(int id) { return new Flashcard(id, this.task, this.sortOrder, checkedOff); }
    public Flashcard withCheckOff(boolean checkOff) { return new Flashcard(id, task, sortOrder, checkOff); }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flashcard flashcard = (Flashcard) o;
        return Objects.equals(id, flashcard.id) && Objects.equals(task, flashcard.task) && Objects.equals(sortOrder, flashcard.sortOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, task, sortOrder);
    }
}