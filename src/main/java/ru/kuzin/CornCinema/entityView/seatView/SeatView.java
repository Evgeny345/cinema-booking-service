package ru.kuzin.CornCinema.entityView.seatView;

import java.util.Comparator;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.Mapping;

import ru.kuzin.CornCinema.models.Seat;

@EntityView(Seat.class)
public interface SeatView extends SeatIdView {
	
	Integer getRowNumber();
	Integer getSeatNumber();
	@Mapping("category.name")
	String getCategory();
	
	static class RowNumberComparator implements Comparator<SeatView> {

		@Override
		public int compare(SeatView s1, SeatView s2) {			
			return Comparator.comparing(SeatView::getRowNumber).thenComparing(SeatView::getSeatNumber).compare(s1, s2);
		}
		
	}

}
