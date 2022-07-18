package com.example.course.service;

import com.example.course.domain.Banner;
import com.example.course.exception.BannerException;
import com.example.course.model.banner.BannerDto;
import com.example.course.repository.BannerRepository;
import com.example.course.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BannerServiceImpl implements BannerService {

    private final BannerRepository bannerRepository;

    @Override
    public String modify(Long id, String name, int sortValue, boolean status,
                        String imageName) {
        Banner banner = bannerRepository.findById(id).orElseThrow(() ->
                new BannerException(ErrorCode.BANNER_NOT_FOUND));
        String oldImgName = banner.getImageName();
        banner.modifyBanner(name, imageName, sortValue, status);
        bannerRepository.save(banner);
        return oldImgName;
    }

    @Override
    public BannerDto select(Long id) {
        Banner banner = bannerRepository.findById(id).orElseThrow(() ->
                new BannerException(ErrorCode.BANNER_NOT_FOUND));
        return BannerDto.fromEntity(banner);
    }

    @Override
    public Page<BannerDto> selectAll(Pageable pageable) {
        int page = pageable.getPageNumber() == 0 ? 0 : pageable.getPageNumber() - 1;
        pageable = PageRequest.of(
                page, pageable.getPageSize(),
                Sort.Direction.DESC,
                "createDate");
        Page<Banner> banners = bannerRepository.findAll(pageable);

        return banners.map(BannerDto::fromEntity);
    }

    @Override
    public void insert(String name, String imageName, int sortValue, boolean status) {
        bannerRepository.save(Banner.builder()
                .name(name)
                .imageName(imageName)
                .sortValue(sortValue)
                .status(status)
                .build());
    }
}