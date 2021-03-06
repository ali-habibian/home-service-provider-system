package ir.maktab.hsps.api.offer;

import ir.maktab.hsps.api.category.sub_category.SubCategoryModel;
import ir.maktab.hsps.entity.HomeServiceOffer;
import ir.maktab.hsps.entity.category.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HomeServiceOfferModel {
    private long id;
    private long proficientId;
    private long orderId;
    private Double suggestedPrice;
    private String workDuration;
    private Instant startTime;
    private double proficientRate;

    public HomeServiceOfferModel convertOffer2Model(HomeServiceOffer homeServiceOffer) {
        return HomeServiceOfferModel.builder()
                .id(homeServiceOffer.getId())
                .proficientId(homeServiceOffer.getProficient().getId())
                .orderId(homeServiceOffer.getHomeServiceOrder().getId())
                .suggestedPrice(homeServiceOffer.getSuggestedPrice())
                .workDuration(homeServiceOffer.getWorkDuration())
                .startTime(homeServiceOffer.getStartTime())
                .proficientRate(homeServiceOffer.getProficient().getRatingAvg())
                .build();
    }
}
