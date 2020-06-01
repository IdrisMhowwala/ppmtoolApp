import {combineReducers} from 'redux';
import errorReducer from './errorReducer';
import projectReducer from './ProjectReducer';
import BacklogReducer from './BacklogReducer';



export default combineReducers({
    errors:errorReducer,
    projects:projectReducer,
    backlog:BacklogReducer
});