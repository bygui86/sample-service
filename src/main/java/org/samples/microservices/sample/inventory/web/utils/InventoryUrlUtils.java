package org.samples.microservices.sample.inventory.web.utils;

import org.samples.microservices.sample.inventory.web.dtos.request.InventoryRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;

import java.util.Collections;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 02 Oct 2018
 */
@Slf4j
public final class InventoryUrlUtils {

	private InventoryUrlUtils() {

		// no-op
	}

	public static HttpEntity<InventoryRequestDto> buildInventoryRequestHttpEntity(final InventoryRequestDto inventoryRequestDto) {

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		return new HttpEntity<>(inventoryRequestDto, headers);
	}

	public static String getInventoryDeleteUrl(final String inventoryUrl, final String productId, final String posId) {

		final StringBuilder stringBuilder = new StringBuilder(inventoryUrl);

		boolean addedParamSeparator = false;
		boolean addedProdIdParam = false;

		if (!StringUtils.isEmpty(productId)) {
			stringBuilder
					.append("?")
					.append("productId=")
					.append(productId);
			addedParamSeparator = true;
			addedProdIdParam = true;
		}

		if (!StringUtils.isEmpty(posId)) {
			stringBuilder
					.append(!addedParamSeparator ? "?" : "")
					.append(addedProdIdParam ? "&" : "")
					.append("posId=")
					.append(posId);
		}

		final String inventoryDeleteUrl = stringBuilder.toString();
		log.debug("Inventory delete url: {}", inventoryDeleteUrl);
		return inventoryDeleteUrl;
	}

}
