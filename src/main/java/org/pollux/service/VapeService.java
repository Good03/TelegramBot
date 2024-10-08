package org.pollux.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.pollux.model.entities.VapeEntity;
import org.pollux.model.interfaces.VapesRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class VapeService {
    private final VapesRepository vapesRepository;

    public List<VapeEntity> getAllVapes() {
        return vapesRepository.findAll();
    }

    public void addVapeToDatabase(long chatId, String vapeInfo) {
        VapeEntity vapeEntity = new VapeEntity();
        String[] vapeDetails = vapeInfo.split(",");

        if(vapeDetails.length == 5) {
            vapeEntity.setBrand(vapeDetails[0].trim());
            vapeEntity.setModel(vapeDetails[1].trim());
            vapeEntity.setFlavor(vapeDetails[2].trim());
            vapeEntity.setPuffs(vapeDetails[3].trim());
            vapeEntity.setPercentsOfNicotine(vapeDetails[4].trim());

            log.info("Parameters of vape was set successfully");
            vapesRepository.save(vapeEntity);
            log.info("Added vape to the database");
        } else {
            log.error("Vape details not valid");
        }
    }
}
