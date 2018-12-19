package sth.core;

import sth.core.exception.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Survey implements Serializable {
    enum State {
        CREATED {
            void open(Survey survey) {
                survey.setState(OPEN);
                Notification note = new Notification("Pode preencher inquérito do projecto " + survey.getProject().getName()
                        + " da disciplina " + survey.getProject().getDisciplineName());
                survey.getProject().notifyDiscipline(note);
            }

            void close(Survey survey) throws ClosingSurveyIdException {
                throw new ClosingSurveyIdException(survey.getProject().getName());
            }

            void cancel(Survey survey) throws NonEmptySurveyIdException{
                if (survey.getNumberAnswer() != 0) {
                    throw new NonEmptySurveyIdException(survey.getProject().getName());
                } else {
                    survey.getProject().surveyWhipeOut();
                }
            }

            void finalized(Survey survey) throws FinishingSurveyIdException {
                throw new FinishingSurveyIdException(survey.getProject().getName());
            }

            String getResultsStudent(Survey survey){
                return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() + " (por abrir)";
            }

            String getResultsTeacher(Survey survey){
                return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() + " (por abrir)";
            }

            String getResultRepresentative(Survey survey){
                return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() + " (por abrir)";
            }

        },
        OPEN {
            void open(Survey survey) throws OpeningSurveyIdException {
                throw new OpeningSurveyIdException(survey.getProject().getName());
            }

            void close(Survey survey) {
                survey.setState(CLOSED);
            }

            void cancel(Survey survey) throws NonEmptySurveyIdException {
                if (survey.getNumberAnswer() != 0) {
                    throw new NonEmptySurveyIdException(survey.getProject().getName());
                } else {
                    survey.getProject().surveyWhipeOut();
                }
            }

            void finalized(Survey survey) throws FinishingSurveyIdException {
                throw new FinishingSurveyIdException(survey.getProject().getName());
            }

            String getResultsStudent(Survey survey){
                return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() + " (aberto)";
            }

            String getResultsTeacher(Survey survey){
                return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() + " (aberto)";
            }

            String getResultRepresentative(Survey survey){
                return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() + " (aberto)";
            }

        },

        CLOSED {
            void open(Survey survey)  {
                Notification note = new Notification("Pode preencher inquérito do projecto " + survey.getProject().getName()
                        + " da disciplina " + survey.getProject().getDisciplineName());
                survey.getProject().notifyDiscipline(note);
                survey.setState(OPEN);
            }

            void close(Survey survey) {
                return;
            }

            void cancel(Survey survey) {
                survey.setState(OPEN);
                Notification note = new Notification("Pode preencher inquérito do projecto " + survey.getProject().getName()
                        + " da disciplina " + survey.getProject().getDisciplineName());
                 survey.getProject().notifyDiscipline(note);
            }

            void finalized(Survey survey) {
                survey.setState(FINALIZED);
                Notification note = new Notification("Resultados do inquérito do projecto " + survey.getProject().getName()
                        + " da disciplina " + survey.getProject().getDisciplineName());
                survey.getProject().notifyDiscipline(note);
            }

            String getResultsStudent(Survey survey){
                return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() + " (fechado)";
            }

            String getResultsTeacher(Survey survey){
                return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() + " (fechado)";
            }

            String getResultRepresentative(Survey survey){
                return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() + " (fechado)";
            }
        },

        FINALIZED {
            void open(Survey survey) throws OpeningSurveyIdException {
                throw new OpeningSurveyIdException(survey.getProject().getName());
            }

            void close(Survey survey) throws ClosingSurveyIdException {
                throw new ClosingSurveyIdException(survey.getProject().getName());
            }

            void cancel(Survey survey) throws SurveyFinishedIdException {
                throw new SurveyFinishedIdException(survey.getProject().getName());
            }

            void finalized(Survey survey) {
                return;
            }

            String getResultsStudent(Survey survey){
                return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() +
                        "\n * Número de respostas: " + survey.getNumberAnswer() +
                        "\n * Tempo médio (horas): " + survey.getAverageNumberHours();
            }

            String getResultsTeacher(Survey survey){
                return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() +
                        "\n * Número de submissões: " + survey.getProject().numberSubmissions() +
                        "\n * Número de respostas: "  + survey.getNumberAnswer() +
                        "\n * Tempos de resolução (horas) (mínimo, médio, máximo): " + survey.getHourMin() + ", "
                            + survey.getAverageNumberHours()
                            + ", " + survey.getHourMax();
            }

            String getResultRepresentative(Survey survey){
                return survey.getProject().getDisciplineName() + " - " + survey.getProject().getName() +
                        " - " +  survey.getNumberAnswer() + " respostas - " + survey.getAverageNumberHours()
                        + " horas";
            }
        }

        ;

        abstract void open(Survey survey) throws OpeningSurveyIdException;

        abstract void close(Survey survey) throws ClosingSurveyIdException;

        abstract void cancel(Survey survey) throws SurveyFinishedIdException, NonEmptySurveyIdException;

        abstract void finalized(Survey survey) throws FinishingSurveyIdException;

        abstract String getResultsStudent(Survey survey);

        abstract String getResultsTeacher(Survey survey);

        abstract String  getResultRepresentative(Survey survey);
    }

    /** Serial number for serialization. */
    private static final long serialVersionUID = 201810051538L;

    private Project _project;
    private List<Answer> _answerLst;
    private State _currentState;
    private int _numberAnswer;
    private int _numberMin;
    private int _numberMax;

    protected Survey(Project project) {
        _project = project;
        _answerLst = new ArrayList<>();
        _numberAnswer = 0;
        _currentState = State.CREATED;

    }

    protected Project getProject() {
        return _project;
    }

    protected int getHourMax(){
        return _numberMax;
    }

    protected int getHourMin(){
        return _numberMin;
    }

    protected void addAnswer(int hours, String mess) throws NoSurveyIdException {
        if (_currentState == State.OPEN) {
            Answer ans = new Answer(hours, mess);
            _answerLst.add(ans);
            if (_numberAnswer == 0){
                _numberMin = hours;
                _numberMax = hours;
            } else {
                _numberMin = Math.min(_numberMin, hours);
                _numberMax = Math.max(_numberMax, hours);
            }
            _numberAnswer++;
        } else {
            throw new NoSurveyIdException(_project.getName());
        }
    }

    protected int getNumberAnswer() {
        return _numberAnswer;
    }

    protected int getAverageNumberHours() {
        if (_numberAnswer == 0){
            return 0;
        }
        int sumHours = 0;
        for (Answer ans : _answerLst) {
            sumHours += ans.getHours();
        }
        return (sumHours / _numberAnswer);
    }

    protected void setState(State state) {
        _currentState = state;
    }

    protected void open() throws OpeningSurveyIdException {
        if (_project.getStatus()) {
            _currentState.open(this);
        } else{
            throw new OpeningSurveyIdException(_project.getName());
        }
    }

    protected void close() throws ClosingSurveyIdException {
        _currentState.close(this);
    }

    protected void cancel() throws SurveyFinishedIdException, NonEmptySurveyIdException {
        _currentState.cancel(this);
    }

    protected void finalized() throws FinishingSurveyIdException {
        _currentState.finalized(this);
    }

    protected String getResultsStudent(){
        return _currentState.getResultsStudent(this);
    }

    protected String getResultTeacher(){
        return _currentState.getResultsTeacher(this);
    }

    protected String getDisciplineResultRepresentative(){
        return _currentState.getResultRepresentative(this);
    }
}