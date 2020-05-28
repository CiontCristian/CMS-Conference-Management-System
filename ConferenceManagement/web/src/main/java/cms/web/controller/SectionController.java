package cms.web.controller;

import cms.core.domain.Section;
import cms.core.service.SectionService;
import cms.web.converter.SectionConverter;
import cms.web.converter.UserConverter;
import cms.web.dto.SectionDTO;
import cms.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class SectionController {
    @Autowired
    private SectionService sectionService;

    @Autowired
    private SectionConverter sectionConverter;

    @Autowired
    private UserConverter userConverter;

    @RequestMapping(value = "section/saveSection", method = RequestMethod.POST)
    SectionDTO saveSection(SectionDTO sectionDTO){
        return sectionConverter.convertModelToDto(sectionService.save(sectionConverter.convertDtoToModel(sectionDTO)));
    }

    @RequestMapping(value = "section/getSectionsByIDs", method = RequestMethod.POST)
    List<SectionDTO> getSectionsByIDs(@RequestBody List<Long> sectionsIDs){
        List<Section> sections = sectionService.getSectionsByIDs(sectionsIDs);
        return new ArrayList<>(sectionConverter.convertModelsToDtos(sections));
    }

    @RequestMapping(value = "section/getSectionsForConference", method = RequestMethod.GET)
    List<SectionDTO> getSectionsForConference(@RequestBody Long conferenceID){
        List<Section> sections = sectionService.getAllByConferenceId(conferenceID);
        return new ArrayList<>(sectionConverter.convertModelsToDtos(sections));
    }

    @RequestMapping(value = "section/getSpeakers", method = RequestMethod.GET)
    List<UserDTO> getSpeakers(@RequestBody Long sectionID){
        Optional<Section> section = sectionService.getSectionById(sectionID);
        if(section.isEmpty()){
            return new ArrayList<>();
        }
        return new ArrayList<>(userConverter.convertModelsToDtos(section.get().getSpeakers()));
    }

    @RequestMapping(value = "section/updateSectionChair/{sectionID}", method = RequestMethod.POST)
    SectionDTO updateSectionChair(@PathVariable Long sectionID, @RequestBody UserDTO userDTO){
        Optional<Section> section = sectionService.updateSectionChair(sectionID, userConverter.convertDtoToModel(userDTO));
        if(section.isEmpty()){
            return null;
        }
        return sectionConverter.convertModelToDto(section.get());
    }

    @RequestMapping(value = "section/updateSectionSpeakers/{sectionID}", method = RequestMethod.POST)
    SectionDTO updateSectionSpeakers(@PathVariable Long sectionID, @RequestBody List<UserDTO> speakers){
        Optional<Section> section = sectionService.updateSectionSpeakers(sectionID, userConverter.convertDtosToModel(speakers));
        if(section.isEmpty()){
            return null;
        }
        return sectionConverter.convertModelToDto(section.get());
    }

}
