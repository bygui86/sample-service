package org.samples.microservices.sample.inventory.unittests.web.xml;

import org.samples.microservices.sample.inventory.web.dtos.request.InventoryQuantityTypeEnumRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryRequestDto;
import org.samples.microservices.sample.inventory.web.dtos.request.InventoryWriteRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.samples.microservices.sample.testingutils.XmlUtils;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Matteo Baiguini
 * matteo@solidarchitectures.com
 * 16 Sep 2018
 */
@RunWith(MockitoJUnitRunner.class)
public class InventoryInboundRequestMarshallingUnitTest extends AbstractInventoryInboundXmlUnitTest {

	@Test
	public void requestMarshallingTest() throws JAXBException {

		// given
		// marshalling > dto to xml
		XmlUtils.createMarshaller(InventoryRequestDto.class, true)
				.marshal(
						createInventoryRequestDto(),
						new File(XML_REQUEST_FILE)
				);

		// when
		// unmarshalling > xml to dto
		final InventoryRequestDto requestDto =
				(InventoryRequestDto) XmlUtils.createUnmarshaller(InventoryRequestDto.class)
						.unmarshal(
								new File(XML_REQUEST_FILE)
						);

		// then
		assertThat(requestDto.getRequestDtos())
				.isNotNull();
		assertThat(requestDto.getRequestDtos().size())
				.isEqualTo(2);

		testRequestDto(requestDto.getRequestDtos().get(0), REQUEST_PRODUCT_ID_1, REQUEST_POS_ID_1, REQUEST_QTY_1);
		testRequestDto(requestDto.getRequestDtos().get(1), REQUEST_PRODUCT_ID_2, REQUEST_POS_ID_2, REQUEST_QTY_2);
	}

	private void testRequestDto(
			final InventoryWriteRequestDto writeRequestDto, final String productId, final String posId, final int qty) {

		Assertions.assertThat(writeRequestDto.getInventoryId())
				.isNotNull();
		assertThat(writeRequestDto.getProductId())
				.isEqualTo(productId);
		assertThat(writeRequestDto.getPosId())
				.isEqualTo(posId);
		assertThat(writeRequestDto.getQuantity())
				.isEqualTo(qty);
		assertThat(writeRequestDto.getQuantityType())
				.isEqualTo(InventoryQuantityTypeEnumRequestDto.AVAILABLE);
	}

	private InventoryRequestDto createInventoryRequestDto() {

		return InventoryRequestDto.builder()
				.requestDtos(
						createInventoryWriteRequestDtos()
				)
				.build();
	}

	private List<InventoryWriteRequestDto> createInventoryWriteRequestDtos() {

		final List<InventoryWriteRequestDto> inventoryWriteDtoList = new ArrayList<>();
		inventoryWriteDtoList.add(
				createInventoryWriteRequestDto(getInventoryId1(), REQUEST_PRODUCT_ID_1, REQUEST_POS_ID_1, REQUEST_QTY_1)
		);
		inventoryWriteDtoList.add(
				createInventoryWriteRequestDto(getInventoryId2(), REQUEST_PRODUCT_ID_2, REQUEST_POS_ID_2, REQUEST_QTY_2)
		);
		return inventoryWriteDtoList;
	}

	private InventoryWriteRequestDto createInventoryWriteRequestDto(
			final String inventoryId1, final String s, final String online, final int i) {

		return InventoryWriteRequestDto.builder()
				.inventoryId(inventoryId1)
				.productId(s)
				.posId(online)
				.quantity(i)
				.quantityType(InventoryQuantityTypeEnumRequestDto.AVAILABLE)
				.build();
	}

}
