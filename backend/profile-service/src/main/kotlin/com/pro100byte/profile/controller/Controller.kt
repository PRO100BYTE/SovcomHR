package com.pro100byte.profile.controller

import com.pro100byte.api.ProfileApi
import com.pro100byte.dto.ProfileEditView
import com.pro100byte.dto.ProfileView
import com.pro100byte.dto.SearchedProfilesView
import com.pro100byte.exception.ServiceException
import com.pro100byte.profile.model.ProfileEdit
import com.pro100byte.profile.service.ProfileService
import com.pro100byte.util.JwtUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.math.BigDecimal
import javax.servlet.http.HttpServletRequest

@RestController
class Controller(
    private val profileService: ProfileService,
    private val httpServletRequest: HttpServletRequest,
    private val jwtUtil: JwtUtil,
) : ProfileApi {
    override fun createInitialProfile(): ResponseEntity<BigDecimal> {
        return ResponseEntity.ok(
            profileService.createInitialProfile().toBigDecimal()
        )
    }

    override fun editProfile(profileEditView: ProfileEditView): ResponseEntity<ProfileView> {
        val profileEdit = ProfileEdit(
            profileEditView.id?.toLong()
                ?: throw ServiceException("No id was passed!", 400),
            profileEditView.firstName,
            profileEditView.lastName,
            profileEditView.patronymic,
            profileEditView.birthDate,
            profileEditView.avatar,
            profileEditView.skillTags,
            profileEditView.location,
            profileEditView.cv,
        )

        val profile = profileService.editProfile(profileEdit)

        return ResponseEntity.ok(
            ProfileView().apply {
                this.id = profile.id?.toBigDecimal()
                    ?: throw ServiceException.smthWentWrong()
                this.firstName = profile.firstName
                this.lastName = profile.lastName
                this.patronymic = profile.patronymic
                this.birthDate = profile.birthDate?.toBigDecimal()
                this.avatar = profile.avatar
                this.cv = profile.cv
                this.location = profile.location
                this.skillTags = profile.rawSkillTags?.split(",")
            }
        )
    }

    override fun getMyProfile(): ResponseEntity<ProfileView> {
        val headerValue = httpServletRequest.getHeader("Authorization")
            ?: throw ServiceException.smthWentWrong()
        val token = headerValue.split(" ").getOrNull(1)
            ?: throw ServiceException.smthWentWrong()

        val myId = jwtUtil.decodeId(token)
        val myProfile = profileService.findMyProfile(myId)

        return ResponseEntity.ok(
            ProfileView().apply {
                this.id = myProfile.id?.toBigDecimal()
                    ?: throw ServiceException.smthWentWrong()
                this.firstName = myProfile.firstName
                this.lastName = myProfile.lastName
                this.patronymic = myProfile.patronymic
                this.birthDate = myProfile.birthDate?.toBigDecimal()
                this.avatar = myProfile.avatar
                this.cv = myProfile.cv
                this.location = myProfile.location
                this.skillTags = myProfile.rawSkillTags?.split(",")
            }
        )
    }

    override fun getProfileById(id: BigDecimal): ResponseEntity<ProfileView> {
        val profile = profileService.findById(id.toLong())
        return ResponseEntity.ok(
            ProfileView().apply {
                this.id = profile.id?.toBigDecimal()
                    ?: throw ServiceException.smthWentWrong()
                this.firstName = profile.firstName
                this.lastName = profile.lastName
                this.patronymic = profile.patronymic
                this.birthDate = profile.birthDate?.toBigDecimal()
                this.avatar = profile.avatar
                this.cv = profile.cv
                this.location = profile.location
                this.skillTags = profile.rawSkillTags?.split(",")
            }
        )
    }

    override fun searchProfile(skillTags: MutableList<String>?, names: MutableList<String>?): ResponseEntity<SearchedProfilesView>? {
        val searchedProfiles = profileService.searchProfiles(
            skillTags, names
        )

        return ResponseEntity.ok(
            SearchedProfilesView().apply {
                this.totalnumber = searchedProfiles.totalnumber.toBigDecimal()
                this.profiles = searchedProfiles.profiles.map {
                    it.map {
                        it.toBigDecimal()
                    }
                }
                this.firstProfiles = searchedProfiles.firstProfiles.map {
                    ProfileView().apply {
                        this.id = it.id?.toBigDecimal()
                        this.firstName = it.firstName
                        this.lastName = it.lastName
                        this.patronymic = it.patronymic
                        this.cv = it.cv
                        this.avatar = it.avatar
                        this.birthDate = it.birthDate?.toBigDecimal()
                        this.location = this.location
                        this.skillTags = it.rawSkillTags?.split(",")
                    }
                }
            }
        )
    }
}
