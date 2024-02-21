package com.prayer.live.msg.dto;

import java.util.List;

/**
 * @program: prayer-live
 * @description:
 * @author: Max Wu
 * @create: 2024-02-15 13:30
 **/
public class LivingRoomScDTO {
	List<MessageDTO> respDTO;

	public List<MessageDTO> getRespDTO() {
		return respDTO;
	}

	public void setRespDTO(List<MessageDTO> respDTO) {
		this.respDTO = respDTO;
	}
}
