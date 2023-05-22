package com.pro100byte.profile.controller

import com.pro100byte.api.ProfileApi
import com.pro100byte.dto.ProfileEditView
import com.pro100byte.dto.ProfileView
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
            profileEditView.passportSerial,
            profileEditView.passportNumber,
            profileEditView.inn,
            profileEditView.cv,
            profileEditView.video,
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
                this.video = profile.video
                this.location = profile.location
                this.verified = profile.verified
                this.enabled = profile.enabled
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
                this.video = myProfile.video
                this.location = myProfile.location
                this.verified = myProfile.verified
                this.enabled = myProfile.enabled
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
                this.video = profile.video
                this.location = profile.location
                this.verified = profile.verified
                this.enabled = profile.enabled
                this.skillTags = profile.rawSkillTags?.split(",")
            }
        )
    }
}
