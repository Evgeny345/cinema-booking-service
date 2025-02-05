package ru.kuzin.CornCinema.entityView.showTimeView;

import com.blazebit.persistence.view.CreatableEntityView;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.UpdatableEntityView;

import ru.kuzin.CornCinema.models.ShowTime;

@EntityView(ShowTime.class)
@CreatableEntityView
@UpdatableEntityView
public interface ShowTimeCreateView extends ShowTimeFormView {

}
