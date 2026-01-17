package az.shopery.model.dto.event;

import az.shopery.model.entity.ShopCreationRequestEntity;

public record ShopCreationRequestApprovedEvent(
        ShopCreationRequestEntity shopCreationRequestEntity) {
}
