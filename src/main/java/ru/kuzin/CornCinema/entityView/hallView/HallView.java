package ru.kuzin.CornCinema.entityView.hallView;

import java.util.Map;
import java.util.Set;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.Mapping;
import com.blazebit.persistence.view.MappingIndex;
import com.blazebit.persistence.view.MultiCollectionMapping;

import ru.kuzin.CornCinema.entityView.seatView.SeatView;
import ru.kuzin.CornCinema.models.Hall;

@EntityView(Hall.class)
public interface HallView extends HallIdView {
	
	String getName();
	@MappingIndex("rowNumber")
	@Mapping("seats")
	@MultiCollectionMapping(comparator = SeatView.RowNumberComparator.class)
	Map<Integer, Set<SeatView>> getSeatsByRows();

}
