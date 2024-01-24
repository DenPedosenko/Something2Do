package com.sysaid.assignment.strategy;

import com.sysaid.assignment.domain.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RatingTaskStrategy implements TaskOfTheDayStrategy {

    private User user;

    @Override
    public void setTaskOfTheDay() {

    }
}
