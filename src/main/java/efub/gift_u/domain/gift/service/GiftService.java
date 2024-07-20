package efub.gift_u.domain.gift.service;

import efub.gift_u.global.S3Image.service.S3ImageService;
import efub.gift_u.domain.gift.dto.GiftRequestDto;
import efub.gift_u.domain.gift.domain.Gift;
import efub.gift_u.domain.funding.domain.Funding;
import efub.gift_u.domain.gift.repository.GiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GiftService {
    private final S3ImageService s3ImageService;
    private final GiftRepository giftRepository;

    public String uploadGiftImage(List<MultipartFile> giftImages, int index) {
        if (giftImages != null && !giftImages.isEmpty() && giftImages.size() > index) {
            MultipartFile giftImage = giftImages.get(index);
            if (giftImage != null && !giftImage.isEmpty()) {
                try {
                    String giftFileName = s3ImageService.upload(giftImage, "images/giftImages");
                    return s3ImageService.getFileUrl(giftFileName);
                } catch (IOException e) {
                    throw new RuntimeException("Gift image upload failed", e);
                }
            }
        }
        return null;
    }

    public List<Gift> createGifts(Funding funding, List<GiftRequestDto> giftDtos, List<MultipartFile> giftImages) {
        return giftDtos.stream()
                .map(giftDto -> {
                    int index = giftDtos.indexOf(giftDto);
                    String giftImageUrl = uploadGiftImage(giftImages, index);
                    return giftDto.toEntity(funding, giftImageUrl);
                })
                .collect(Collectors.toList());
    }

    public void saveAll(List<Gift> gifts) {
        giftRepository.saveAll(gifts);
    }

    // S3 URL에서 Key를 추출
    private String extractS3KeyFromUrl(String url) {
        String keyPrefix = ".com/";
        int startIndex = url.indexOf(keyPrefix) + keyPrefix.length(); // .com/ 뒤의 문자열부터 Key 시작
        return url.substring(startIndex);
    }


    public void deleteGifts(Funding funding) {
        List<Gift> gifts = funding.getGiftList();
        for (Gift gift : gifts) {
            if (gift.getGiftImageUrl() != null) {
                String giftImageKey = extractS3KeyFromUrl(gift.getGiftImageUrl());
                s3ImageService.delete(giftImageKey);
            }
            giftRepository.delete(gift);
        }
    }



}



