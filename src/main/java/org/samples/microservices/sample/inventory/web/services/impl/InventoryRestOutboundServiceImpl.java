package org.samples.microservices.sample.inventory.web.services.impl;

import org.samples.microservices.sample.constants.ProfileConstants;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryResponseDto;
import org.samples.microservices.sample.inventory.web.services.InventoryOutboundService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.samples.microservices.sample.inventory.web.utils.InventoryUrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.springframework.http.HttpMethod.PUT;


@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter(value = AccessLevel.PROTECTED)
@Profile(ProfileConstants.SPRING_PROFILE_OUTBOUND_REST)
@Service("inventoryOutboundService")
public class InventoryRestOutboundServiceImpl implements InventoryOutboundService {

	static final String INVENTORY_DELETE_ALL_POSID_PARAM_KEY = "posId";

	@Value("${outbound.rest.other.service.inventories.url}")
	String inventoryUrl;

	@Autowired
	@Qualifier("outboundRestTemplate")
	RestTemplate outboundRestTemplate;

	@Override
	public InventoryResponseDto insert(final List<InventoryWriteRequestDto> inventoryWriteRequestDtos) {

		return insert(
				InventoryRequestDto.builder().requestDtos(inventoryWriteRequestDtos).build()
		);
	}

	@Override
	public InventoryResponseDto update(final List<InventoryWriteRequestDto> inventoryWriteRequestDtos) {

		return update(
				InventoryRequestDto.builder().requestDtos(inventoryWriteRequestDtos).build()
		);
	}

	@Override
	public void delete(final String productId, final String posId) {

		log.debug("Delete all inventories for productId {} and posId {}", productId, posId);

		getOutboundRestTemplate().delete(
				InventoryUrlUtils.getInventoryDeleteUrl(getInventoryUrl(), productId, posId)
		);
	}

	protected InventoryResponseDto insert(final InventoryRequestDto inventoryRequestDto) {

		log.debug("{} Inventories to INSERT...", inventoryRequestDto.getRequestDtos().size());

		return getOutboundRestTemplate()
				.postForObject(
						getInventoryUrl(),
						InventoryUrlUtils.buildInventoryRequestHttpEntity(inventoryRequestDto),
						InventoryResponseDto.class);
	}

	protected InventoryResponseDto update(final InventoryRequestDto inventoryRequestDto) {

		log.debug("{} Inventories to UPDATE...", inventoryRequestDto.getRequestDtos().size());

		return getOutboundRestTemplate()
				.exchange(
						getInventoryUrl(),
						PUT,
						InventoryUrlUtils.buildInventoryRequestHttpEntity(inventoryRequestDto),
						InventoryResponseDto.class
				).getBody();
	}

}
