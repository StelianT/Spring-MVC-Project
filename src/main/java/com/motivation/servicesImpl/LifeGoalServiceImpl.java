package com.motivation.servicesImpl;

import com.motivation.entities.LifeGoal;
import com.motivation.models.bindingModels.AddLifeGoalBindingModel;
import com.motivation.models.viewModels.LifeGoalViewModel;
import com.motivation.repository.LifeGoalRepository;
import com.motivation.services.LifeGoalService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LifeGoalServiceImpl implements LifeGoalService {

    private final LifeGoalRepository lifeGoalRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public LifeGoalServiceImpl(LifeGoalRepository lifeGoalRepository, ModelMapper modelMapper) {
        this.lifeGoalRepository = lifeGoalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<LifeGoalViewModel> findAllLifeGoalsByUserId(Long userId) {
        List<LifeGoal> lifeGoals = this.lifeGoalRepository.findAllByUserId(userId);
        List<LifeGoalViewModel> models = new ArrayList<>();
        for (LifeGoal lifeGoal : lifeGoals) {
            models.add(this.modelMapper.map(lifeGoal, LifeGoalViewModel.class));
        }

        return models;
    }

    @Override
    public void save(AddLifeGoalBindingModel addLifeGoalBindingModel) {
        LifeGoal lifeGoal = this.modelMapper.map(addLifeGoalBindingModel, LifeGoal.class);
        lifeGoal.setCompleted(false);
        this.lifeGoalRepository.save(lifeGoal);
    }

    @Override
    public void completeLifeGoal(long lifeGoalId) {
        LifeGoal lifeGoal = this.lifeGoalRepository.getOne(lifeGoalId);
        lifeGoal.setCompleted(true);
        this.lifeGoalRepository.saveAndFlush(lifeGoal);
    }
}
