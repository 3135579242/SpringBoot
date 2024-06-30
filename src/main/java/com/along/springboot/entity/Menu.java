package com.along.springboot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Builder
@Data
@TableName("menu")
public final class Menu {
    private final Integer id;
    private final String menuName;
    private final List<String> premKey;



    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Menu) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.menuName, that.menuName) &&
                Objects.equals(this.premKey, that.premKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, menuName, premKey);
    }

    @Override
    public String toString() {
        return "Menu[" +
                "id=" + id + ", " +
                "menuName=" + menuName + ", " +
                "premKey=" + premKey + ']';
    }

}
