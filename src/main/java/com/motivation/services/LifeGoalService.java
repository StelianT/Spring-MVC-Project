package com.motivation.services;

import com.motivation.models.bindingModels.AddLifeGoalBindingModel;
import com.motivation.models.viewModels.LifeGoalViewModel;

import java.util.List;

public interface LifeGoalService {

    List<LifeGoalViewModel> findAllLifeGoalsByUserId(Long userId);

    void save(AddLifeGoalBindingModel addLifeGoalBindingModel);

    void completeLifeGoal(long lifeGoalId);
}
