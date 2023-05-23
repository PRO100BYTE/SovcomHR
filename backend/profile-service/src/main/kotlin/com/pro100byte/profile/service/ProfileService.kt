package com.pro100byte.profile.service

import com.pro100byte.exception.ServiceException
import com.pro100byte.profile.model.*
import com.pro100byte.profile.repository.NameSearchRepository
import com.pro100byte.profile.repository.ProfileRepository
import com.pro100byte.profile.repository.SkillTagRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ProfileService(
    private val profileRepository: ProfileRepository,
    private val skillTagRepository: SkillTagRepository,
    private val nameSearchRepository: NameSearchRepository,
) {
    private val PAGE_SIZE = 10

    fun createInitialProfile(): Long {
        val profile = Profile()
        return profileRepository.save(profile).id
            ?: throw ServiceException.smthWentWrong()
    }

    @Transactional
    fun editProfile(profileEdit: ProfileEdit): Profile {
        val profile = profileRepository.findByIdOrNull(profileEdit.id)
            ?: throw ServiceException("Couldnt find profile with id: ${profileEdit.id}", 404)

        profileEdit.avatar?.let {
            profile.avatar = it
        }
        profileEdit.firstName?.let {
            profile.nameSearch += NameSearch().apply {
                this.name = it
                this.profile = profile
            }
            profile.firstName = it
        }
        profileEdit.lastName?.let {
            profile.nameSearch += NameSearch().apply {
                this.name = it
                this.profile = profile
            }
            profile.lastName = it
        }
        profileEdit.patronymic?.let {
            profile.patronymic = it
        }
        profileEdit.birthDate?.let {
            profile.birthDate = it.toLong()
        }
        profileEdit.location?.let {
            profile.location = it
        }
        profileEdit.cv?.let {
            profile.cv = it
        }
        profileEdit.skillTags?.let {
            profile.rawSkillTags = it.joinToString(separator = ",")
        }

        profileEdit.skillTags?.distinct()?.map {
            SkillTag().apply {
                this.skillTag = it.lowercase()
                this.profiles?.add(profile)
            }
        }?.let {
            profile.skillTags?.addAll(it)
        }

        return profileRepository.save(profile)
    }

    fun findById(id: Long): Profile {
        return profileRepository.findByIdOrNull(id)
            ?: throw ServiceException("Couldnt find profile with id: $id", 404)
    }

    fun findMyProfile(id: Long): Profile {
        return profileRepository.findByIdOrNull(id)
            ?: throw ServiceException("Couldnt find your profile", 404)
    }

    fun searchProfiles(skillTags: List<String>?, names: List<String>?): SearchedProfiles {
        val profilesByName = names
            ?.mapNotNull {
                nameSearchRepository.findAllByName(it).mapNotNull {
                    it.profile?.id
                }
            }
            ?.flatten()
            ?.groupBy {
                it
            }
            ?.filter { it.value.size == names.size }
            ?.map { it.key } ?: listOf()

        val profilesBySkillTag = skillTags
            ?.mapNotNull {
                skillTagRepository.findByIdOrNull(it.lowercase())?.profiles?.mapNotNull {
                    it.id
                }
            }
            ?.flatten()
            ?.groupBy {
                it
            }
            ?.filter { it.value.size == skillTags.size }
            ?.map { it.key }
            ?.toList() ?: listOf()

        val foundProfiles = (profilesByName + profilesBySkillTag).groupBy {
            it
        }.filter {
            it.value.size == (skillTags?.let { 1 } ?: 0) + (names?.let { 1 } ?: 0)
        }.keys

        return SearchedProfiles(
            totalnumber = foundProfiles.size.toLong(),
            profiles = foundProfiles.chunked(PAGE_SIZE),
            firstProfiles = foundProfiles.take(PAGE_SIZE).mapNotNull {
                profileRepository.findByIdOrNull(it)
            }
        )
    }
}
