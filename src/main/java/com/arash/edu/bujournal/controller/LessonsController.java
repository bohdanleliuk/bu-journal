package com.arash.edu.bujournal.controller;

import com.arash.edu.bujournal.domain.*;
import com.arash.edu.bujournal.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Controller
public class LessonsController {

    private final LessonService lessonService;
    private final SubjectService subjectService;
    private final SourceService sourceService;
    private final TeacherService teacherService;
    private final GroupService groupService;

    @GetMapping("/lessons/{lessonId}")
    public String showLesson(@PathVariable UUID lessonId, Model model) {
        Lesson lesson = lessonService.findById(lessonId);
        List<Source> sources = sourceService.findAllByLessonId(lesson.getId());
        Subject subject = subjectService.findById(lesson.getSubjectId());
        Teacher teacher = teacherService.findByNullableId(subject.getTeacherId());
        Group group = groupService.findById(subject.getGroupId());
        model.addAttribute("lesson", lesson);
        model.addAttribute("sources", sources);
        model.addAttribute("subject", subject);
        model.addAttribute("teacher", teacher);
        model.addAttribute("group", group);
        model.addAttribute("addSourceDraft", new Source());
        return "lesson";
    }

    @PostMapping("/lessons/{lessonId}/sources")
    public String addSourceToLesson(@PathVariable UUID lessonId, @ModelAttribute Source source) {
        sourceService.addSource(source, lessonId);
        return "redirect:/lessons/" + lessonId;
    }

    @GetMapping("/lessons/{lessonId}/sources/{sourceId}/draft")
    public String getSourceOfLessonDraft(@PathVariable UUID lessonId, @PathVariable UUID sourceId, RedirectAttributes redirectAttributes) {
        Source source = sourceService.findById(sourceId);
        redirectAttributes.addFlashAttribute("editSourceDraft", source);
        return "redirect:/lessons/" + lessonId;
    }

    @PostMapping("/lessons/{lessonId}/sources/{sourceId}")
    public String editSourceOfLesson(@PathVariable UUID lessonId, @PathVariable UUID sourceId, @ModelAttribute Source source) {
        sourceService.editSource(sourceId, source);
        return "redirect:/lessons/" + lessonId;
    }

    @GetMapping("/lessons/{lessonId}/sources/{sourceId}/delete")
    public String deleteSourceOfLesson(@PathVariable UUID lessonId, @PathVariable UUID sourceId, RedirectAttributes redirectAttributes) {
        Source source = sourceService.findById(sourceId);
        redirectAttributes.addFlashAttribute("sourceDeleteCandidate", source);
        return "redirect:/lessons/" + lessonId;
    }

    @GetMapping("/lessons/{lessonId}/sources/{sourceId}/delete/confirm")
    public String confirmDeleteSourceOfLesson(@PathVariable UUID lessonId, @PathVariable UUID sourceId) {
        sourceService.deleteSource(sourceId);
        return "redirect:/lessons/" + lessonId;
    }
}
