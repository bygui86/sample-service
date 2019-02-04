package org.samples.microservices.sample.inventory.unittests.web.xml;

import org.samples.microservices.sample.inventory.web.dtos.response.InventoryResponseDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryStatusEnumResponseDto;
import org.samples.microservices.sample.inventory.web.dtos.response.InventoryWriteResponseDto;
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
public class InventoryInboundResponseMarshallingUnitTest extends AbstractInventoryInboundXmlUnitTest {

	@Test
	public void responseMarshallingTest() throws JAXBException {

		// given
		// marshalling > dto to xml
		XmlUtils.createMarshaller(InventoryResponseDto.class, true)
				.marshal(
						createInventoryResponseDto(),
						new File(XML_RESPONSE_FILE)
				);

		// when
		// unmarshalling > xml to dto
		final InventoryResponseDto requestDto =
				(InventoryResponseDto) XmlUtils.createUnmarshaller(InventoryResponseDto.class)
						.unmarshal(
								new File(XML_RESPONSE_FILE)
						);

		// then
		assertThat(requestDto.getResponses())
				.isNotNull();
		assertThat(requestDto.getResponses().size())
				.isEqualTo(2);

		testResponseDto(requestDto.getResponses().get(0), InventoryStatusEnumResponseDto.OK, RESPONSE_ERROR_MSG_1);
		testResponseDto(requestDto.getResponses().get(1), InventoryStatusEnumResponseDto.ERR, RESPONSE_ERROR_MSG_2);
	}

	private void testResponseDto(
			final InventoryWriteResponseDto writeResponseDto, final InventoryStatusEnumResponseDto status, final String errMsg) {

		assertThat(writeResponseDto.getInventoryId())
				.isNotNull();
		assertThat(writeResponseDto.getStatus())
				.isEqualTo(status);
		assertThat(writeResponseDto.getErrorMessage())
				.isEqualTo(errMsg);
	}

	private InventoryResponseDto createInventoryResponseDto() {

		return InventoryResponseDto.builder()
				.responses(
						createInventoryWriteResponseDtos()
				)
				.build();
	}

	private List<InventoryWriteResponseDto> createInventoryWriteResponseDtos() {

		final List<InventoryWriteResponseDto> inventoryWriteDtoList = new ArrayList<>();
		inventoryWriteDtoList.add(
				createInventoryWriteResponseDto(getInventoryId1(), InventoryStatusEnumResponseDto.OK, RESPONSE_ERROR_MSG_1)
		);
		inventoryWriteDtoList.add(
				createInventoryWriteResponseDto(getInventoryId2(), InventoryStatusEnumResponseDto.ERR, RESPONSE_ERROR_MSG_2)
		);
		return inventoryWriteDtoList;
	}

	private InventoryWriteResponseDto createInventoryWriteResponseDto(
			final String intentoryId, final InventoryStatusEnumResponseDto status, final String errorMsg) {

		return InventoryWriteResponseDto.builder()
				.inventoryId(intentoryId)
				.status(status)
				.errorMessage(errorMsg)
				.build();
	}

}
