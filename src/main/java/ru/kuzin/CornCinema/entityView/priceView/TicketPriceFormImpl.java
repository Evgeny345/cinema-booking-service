package ru.kuzin.CornCinema.entityView.priceView;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.SerializableEntityViewManager;
import com.blazebit.persistence.view.StaticImplementation;
import com.blazebit.persistence.view.spi.type.EntityViewProxy;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import ru.kuzin.CornCinema.models.Price;

@StaticImplementation(TicketPriceForm.class)
public class TicketPriceFormImpl implements TicketPriceForm, EntityViewProxy {
	
	public static volatile EntityViewManager ENTITY_VIEW_MANAGER;
	public static final SerializableEntityViewManager SERIALIZABLE_ENTITY_VIEW_MANAGER = new SerializableEntityViewManager(TicketPriceFormImpl.class, ENTITY_VIEW_MANAGER);

	private Integer seatCategoryId;
	private String seatCategoryName;
	@DecimalMin(value = "0.0", inclusive = false, message = "must be more than 0.0")
    @Digits(integer=4, fraction=2)
	private BigDecimal price = new BigDecimal(0.0);
	
	public TicketPriceFormImpl() {
		
	}
	
	public TicketPriceFormImpl(TicketPriceFormImpl noop, Map<String, Object> optionalParameters) {
		this.seatCategoryId = null;
		this.seatCategoryName = null;
		this.price = null;
	}
	
	public TicketPriceFormImpl(Integer seatCategoryId) {
		this.seatCategoryId = seatCategoryId;
		this.seatCategoryName = null;
		this.price = null;
	}
	
	public TicketPriceFormImpl(Integer seatCategoryId, String seatCategoryName, BigDecimal price) {
		this.seatCategoryId = seatCategoryId;
		this.seatCategoryName = seatCategoryName;
		this.price = price;
	}
	
	public TicketPriceFormImpl(Integer seatCategoryId, String seatCategoryName) {
		this.seatCategoryId = seatCategoryId;
		this.seatCategoryName = seatCategoryName;
	}
	
	public TicketPriceFormImpl(TicketPriceFormImpl noop, int offset, Object[] tuple) {
		this.seatCategoryId = (Integer) tuple[offset + 0];
		this.seatCategoryName = (String) tuple[offset + 1];
		this.price = (BigDecimal) tuple[offset + 2];
	}
	
	@Override
	public Integer getSeatCategoryId() {return seatCategoryId;}
	@Override
	public String getSeatCategoryName() {return seatCategoryName;}
	@Override
	public BigDecimal getPrice() {return price;}

	public void setSeatCategoryId(Integer seatCategoryId) {this.seatCategoryId = seatCategoryId;}
	public void setSeatCategoryName(String seatCategoryName) {this.seatCategoryName = seatCategoryName;}
	public void setPrice(BigDecimal price) {this.price = price;}

	@Override
	public Class<?> $$_getJpaManagedClass() {
		return Price.class;
	}

	@Override
	public Class<?> $$_getJpaManagedBaseClass() {
		return Price.class;
	}

	@Override
	public Class<?> $$_getEntityViewClass() {
		return TicketPriceForm.class;
	}

	@Override
	public boolean $$_isNew() {
		return false;
	}

	@Override
	public boolean $$_isReference() {
		return false;
	}

	@Override
	public void $$_setIsReference(boolean isReference) {
		
	}

	@Override
	public Object $$_getId() {
		return seatCategoryId;
	}

	@Override
	public Object $$_getVersion() {
		return null;
	}
	
	@Override
    public int hashCode() {
        return Objects.hashCode(seatCategoryId);
    }
	
	@Override
    public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
        if (obj == null || this.$$_getId() == null) {
            return false;
        }
        if (obj instanceof EntityViewProxy) {
            EntityViewProxy other = (EntityViewProxy) obj;
            if (this.$$_getJpaManagedBaseClass() == other.$$_getJpaManagedBaseClass() && this.$$_getId().equals(other.$$_getId())) {
                return true;
            } else {
                return false;
            }
        }
        if (obj instanceof TicketPriceForm) {
        	TicketPriceForm other = (TicketPriceForm) obj;
            if (!Objects.equals(this.getSeatCategoryId(), other.getSeatCategoryId())) {
                return false;
            }
            return true;
        }
		return false;
	}

}
