package ru.kuzin.CornCinema.entityView.movieView;

import com.blazebit.persistence.SubqueryInitiator;
import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.MappingSubquery;
import com.blazebit.persistence.view.SubqueryProvider;

import ru.kuzin.CornCinema.models.Movie;
import ru.kuzin.CornCinema.models.ShowTime;

@EntityView(Movie.class)
public interface MovieWithSubqueryView extends MovieView {
	
	@MappingSubquery(ShowTimesBeginningSinceSubquery.class)
	Long getShowTimesAmountSinceDate();
	
	class ShowTimesBeginningSinceSubquery implements SubqueryProvider {

        @Override
        public <T> T createSubquery(SubqueryInitiator<T> subqueryBuilder) {
        	
				return subqueryBuilder.from(ShowTime.class, "st")
						.select("COUNT(*)")
				        .where("st.movie.id").eqExpression("VIEW(id)")
				            .whereOr()
				            	.where("st.startTime").gtExpression(":showTimesExistSince")
				            	.where("st.startTime").eqExpression(":showTimesExistSince")
				            .endOr()
				        .end();
        }
    }

}
